/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.aeontronix.commons;

import javax.jms.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import java.io.Serializable;
import java.io.StringWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import static com.aeontronix.commons.JMSUtils.DestType.QUEUE;
import static com.aeontronix.commons.JMSUtils.DestType.TOPIC;

/**
 * Various JMS utilities
 */
public class JMSUtils {
    private static final Logger logger = Logger.getLogger(JMSUtils.class.getName());

    public static void sendTransactedMessage(ConnectionFactory connectionFactory, Destination dest,
                                             MessageGenerator messageGenerator, final Map<String, Object> msgProps) throws JMSException {
        final Connection connection = connectionFactory.createConnection();
        Session session = null;
        MessageProducer producer = null;
        try {
            session = connection.createSession(true, Session.SESSION_TRANSACTED);
            if (dest instanceof DestOnDemand) {
                String destName = ((DestOnDemand) dest).destName;
                DestType destType = ((DestOnDemand) dest).destType;
                if (StringUtils.isNotEmpty(destName)) {
                    if (destType == null) {
                        throw new JMSException("Destination type not specified");
                    }
                    switch (destType) {
                        case QUEUE:
                            dest = session.createQueue(destName);
                            break;
                        case TOPIC:
                            dest = session.createTopic(destName);
                            break;
                        default:
                            throw new JMSException("Destination type not supported");
                    }
                } else {
                    throw new JMSException("No destination specified");
                }
            }
            producer = session.createProducer(dest);
            final Message message = messageGenerator.createMessage(session);
            setMsgProperties(message, msgProps);
            producer.send(message);
        } finally {
            close(session, connection, producer);
        }
    }

    public static void sendTransactedMessage(ConnectionFactory connectionFactory, Destination dest,
                                             final Map<String, Object> msgProps) throws JMSException {
        sendTransactedMessage(connectionFactory, dest, new MessageGenerator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return setMsgProperties(session.createMessage(), msgProps);
            }
        });
    }

    public static void sendTransactedObjectMessage(ConnectionFactory connectionFactory, Destination dest,
                                                   final Serializable object, final Map<String, Object> msgProps) throws JMSException {
        sendTransactedMessage(connectionFactory, dest, new MessageGenerator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return setMsgProperties(session.createObjectMessage(object), msgProps);
            }
        });
    }

    public static void sendTransactedTextMessageToQueue(ConnectionFactory connectionFactory, String queueName,
                                                        final String text, final Map<String, Object> msgProps) throws JMSException {
        sendTransactedTextMessage(connectionFactory, new DestOnDemand(queueName, QUEUE), text, msgProps);
    }

    public static void sendTransactedTextMessageToTopic(ConnectionFactory connectionFactory, String topicName,
                                                        final String text, final Map<String, Object> msgProps) throws JMSException {
        sendTransactedTextMessage(connectionFactory, new DestOnDemand(topicName, TOPIC), text, msgProps);
    }

    public static void sendTransactedTextMessage(ConnectionFactory connectionFactory, Destination dest,
                                                 final String text, final Map<String, Object> msgProps) throws JMSException {
        sendTransactedMessage(connectionFactory, dest, new MessageGenerator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                return session.createTextMessage(text);
            }
        }, msgProps);
    }

    public static void sendTransactedJAXBMessage(ConnectionFactory connectionFactory, Destination dest,
                                                 final Object object, final Map<String, Object> msgProps) throws JMSException, JAXBException {
        final JAXBContext jaxbContext = JAXBContext.newInstance(object.getClass());
        final Marshaller marshaller = jaxbContext.createMarshaller();
        StringWriter xml = new StringWriter();
        marshaller.marshal(object, xml);
        sendTransactedTextMessage(connectionFactory, dest, xml.toString(), msgProps);
    }

    public static void sendTransactedMessage(ConnectionFactory connectionFactory, Destination dest,
                                             MessageGenerator messageGenerator) throws JMSException {
        sendTransactedMessage(connectionFactory, dest, messageGenerator, null);
    }

    public static void sendTransactedMessageToQueue(ConnectionFactory connectionFactory, String queueName,
                                                    MessageGenerator messageGenerator) throws JMSException {
        sendTransactedMessageToQueue(connectionFactory, queueName, messageGenerator, null);
    }

    public static void sendTransactedMessageToQueue(ConnectionFactory connectionFactory, String queueName,
                                                    MessageGenerator messageGenerator, final Map<String, Object> msgProps)
            throws JMSException {
        sendTransactedMessage(connectionFactory, new DestOnDemand(queueName, QUEUE), messageGenerator, msgProps);
    }

    public static void sendTransactedMessageToQueue(ConnectionFactory connectionFactory, String queueName,
                                                    final Map<String, Object> msgProps) throws JMSException {
        sendTransactedMessageToQueue(connectionFactory, queueName, new PlainMsgGenerator(), msgProps);
    }

    public static void sendTransactedMessageToTopic(ConnectionFactory connectionFactory, String topicName,
                                                    MessageGenerator messageGenerator) throws JMSException {
        sendTransactedMessageToTopic(connectionFactory, topicName, messageGenerator, null);
    }

    public static void sendTransactedMessageToTopic(ConnectionFactory connectionFactory, String topicName,
                                                    MessageGenerator messageGenerator, final Map<String, Object> msgProps)
            throws JMSException {
        sendTransactedMessage(connectionFactory, new DestOnDemand(topicName, TOPIC), messageGenerator, msgProps);
    }

    public static void sendTransactedMessageToTopic(ConnectionFactory connectionFactory, String topicName,
                                                    final Map<String, Object> msgProps) throws JMSException {
        sendTransactedMessageToTopic(connectionFactory, topicName, new PlainMsgGenerator(), msgProps);
    }

    public static boolean hasProperties(Message message, String... propnames) throws JMSException {
        for (String name : propnames) {
            if (!message.propertyExists(name)) {
                return false;
            }
        }
        return true;
    }

    public static void close(Session session, Connection connection, MessageProducer producer) {
        close(session, connection, producer, null);
    }

    public static void close(Session session, Connection connection, MessageConsumer consumer) {
        close(session, connection, null, consumer);
    }

    public static void close(Session session, Connection connection, MessageProducer producer, MessageConsumer consumer) {
        if (session != null) {
            try {
                session.close();
            } catch (JMSException e) {
                logger.log(Level.WARNING, "Error closing JMS session", e);
            }
        }
        if (connection != null) {
            try {
                connection.close();
            } catch (JMSException e) {
                logger.log(Level.WARNING, "Error closing JMS connection", e);
            }
        }
        if (producer != null) {
            try {
                producer.close();
            } catch (JMSException e) {
                logger.log(Level.WARNING, "Error closing JMS producer", e);
            }
        }
        if (consumer != null) {
            try {
                consumer.close();
            } catch (JMSException e) {
                logger.log(Level.WARNING, "Error closing JMS consumer", e);
            }
        }
    }

    public static Message setMsgProperties(Message msg, Map<String, Object> params) throws JMSException {
        if (params != null && !params.isEmpty()) {
            for (Map.Entry<String, Object> entry : params.entrySet()) {
                final String key = entry.getKey();
                final Object value = entry.getValue();
                if (value instanceof String) {
                    msg.setStringProperty(key, ((String) value));
                } else if (value instanceof Boolean) {
                    msg.setBooleanProperty(key, (Boolean) value);
                } else if (value instanceof Byte) {
                    msg.setByteProperty(key, (Byte) value);
                } else if (value instanceof Double) {
                    msg.setDoubleProperty(key, (Double) value);
                } else if (value instanceof Float) {
                    msg.setFloatProperty(key, (Float) value);
                } else if (value instanceof Integer) {
                    msg.setIntProperty(key, (Integer) value);
                } else if (value instanceof Short) {
                    msg.setShortProperty(key, (Short) value);
                } else if (value instanceof Long) {
                    msg.setLongProperty(key, (Long) value);
                } else {
                    msg.setObjectProperty(key, value);
                }
            }
        }
        return msg;
    }

    public static Message clone(Session session, Message message, boolean setCorrelationId) throws JMSException {
        if (message instanceof MapMessage) {
            final MapMessage mapMessage = (MapMessage) message;
            MapMessage clone = session.createMapMessage();
            copyHeaders(message, clone, setCorrelationId);
            final Enumeration<?> names = mapMessage.getMapNames();
            while (names.hasMoreElements()) {
                String name = (String) names.nextElement();
                clone.setObject(name, mapMessage.getObject(name));
            }
            return clone;
        } else if (message instanceof BytesMessage) {
            final BytesMessage bytesMessage = (BytesMessage) message;
            final BytesMessage clone = session.createBytesMessage();
            copyHeaders(message, clone, setCorrelationId);
            byte[] buffer = new byte[1024];
            for (int i = bytesMessage.readBytes(buffer); i != -1; i = bytesMessage.readBytes(buffer)) {
                clone.writeBytes(buffer, 0, i);
            }
            return clone;
        } else if (message instanceof ObjectMessage) {
            final Serializable object = ((ObjectMessage) message).getObject();
            if (object != null) {
                return session.createObjectMessage(object);
            } else {
                return session.createObjectMessage();
            }
        } else if (message instanceof TextMessage) {
            final String text = ((TextMessage) message).getText();
            if (text != null) {
                return session.createTextMessage(text);
            } else {
                return session.createTextMessage();
            }
        } else if (message instanceof StreamMessage) {
            final StreamMessage clone = session.createStreamMessage();
            StreamMessage streamMessage = (StreamMessage) message;
            byte[] buffer = new byte[1024];
            for (int i = streamMessage.readBytes(buffer); i != -1; i = streamMessage.readBytes(buffer)) {
                clone.writeBytes(buffer, 0, i);
            }
            return clone;
        } else {
            throw new IllegalArgumentException("Invalid message class: " + message.getClass());
        }
    }

    private static void copyHeaders(Message message, Message clone, boolean setCorrelationId) throws JMSException {
        final Enumeration<?> propertyNames = message.getPropertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            clone.setObjectProperty(name, message.getObjectProperty(name));
        }
        if (setCorrelationId) {
            clone.setJMSCorrelationID(message.getJMSMessageID());
        }
    }

    public static abstract class MessageGenerator {
        protected MessageGenerator() {
        }

        public abstract Message createMessage(Session session) throws JMSException;
    }

    public static class PlainMsgGenerator extends MessageGenerator {
        @Override
        public Message createMessage(Session session) throws JMSException {
            return session.createMessage();
        }
    }

    public static class DestOnDemand implements Destination {
        private String destName;
        private DestType destType;

        public DestOnDemand(String destName, DestType destType) {
            this.destName = destName;
            this.destType = destType;
        }
    }

    public enum DestType {
        QUEUE, TOPIC
    }
}
