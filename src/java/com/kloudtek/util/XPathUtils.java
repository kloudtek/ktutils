/*
 * Copyright (c) Kloudtek Ltd 2013.
 */

package com.kloudtek.util;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import javax.xml.namespace.NamespaceContext;
import javax.xml.namespace.QName;
import javax.xml.xpath.*;
import java.lang.ref.SoftReference;
import java.util.*;

public class XPathUtils {
    private static final WeakHashMap<Thread, SoftReference<XPath>> xpathCache = new WeakHashMap<Thread, SoftReference<XPath>>();

    public static XPathFactory newXPathFactory() {
        return XPathFactory.newInstance();
    }

    public static XPathExpression newXPath(String xpath) throws XPathExpressionException {
        return newXPathFactory().newXPath().compile(xpath);
    }

    public static XPathExpression newXPath(String xpath, final Map<String, String> namespaces) throws XPathExpressionException {
        final XPath xp = newXPathFactory().newXPath();
        xp.setNamespaceContext(new NamespaceContext() {
            @Override
            public String getNamespaceURI(String prefix) {
                return namespaces != null ? namespaces.get(prefix) : null;
            }

            @Override
            public String getPrefix(String namespaceURI) {
                if (namespaces == null) {
                    return null;
                } else {
                    final Iterator i = getPrefixes(namespaceURI);
                    if (i.hasNext()) {
                        return (String) i.next();
                    } else {
                        return null;
                    }
                }
            }

            @Override
            public Iterator getPrefixes(String namespaceURI) {
                if (namespaces == null) {
                    return null;
                } else {
                    ArrayList<String> list = new ArrayList<String>();
                    for (Map.Entry<String, String> entry : namespaces.entrySet()) {
                        if (entry.getValue().equals(namespaceURI)) {
                            list.add(entry.getKey());
                        }
                    }
                    return list.iterator();
                }
            }
        });
        return xp.compile(xpath);
    }

    public static Object evalXPath(String xpath, InputSource source, QName type) throws XPathExpressionException {
        return newXPath(xpath).evaluate(source, type);
    }

    public static Object evalXPath(String xpath, Node source, QName type) throws XPathExpressionException {
        return newXPath(xpath).evaluate(source, type);
    }

    public static Node evalXPathNode(String xpath, InputSource source) throws XPathExpressionException {
        return (Node) newXPath(xpath).evaluate(source, XPathConstants.NODE);
    }

    public static Node evalXPathNode(String xpath, Node source) throws XPathExpressionException {
        return (Node) newXPath(xpath).evaluate(source, XPathConstants.NODE);
    }

    public static Element evalXPathElement(String xpath, InputSource source) throws XPathExpressionException {
        return (Element) newXPath(xpath).evaluate(source, XPathConstants.NODE);
    }

    public static Element evalXPathElement(String xpath, Node source) throws XPathExpressionException {
        return (Element) newXPath(xpath).evaluate(source, XPathConstants.NODE);
    }

    public static Boolean evalXPathBoolean(String xpath, InputSource source) throws XPathExpressionException {
        return (Boolean) newXPath(xpath).evaluate(source, XPathConstants.BOOLEAN);
    }

    public static Boolean evalXPathBoolean(String xpath, Node source) throws XPathExpressionException {
        return (Boolean) newXPath(xpath).evaluate(source, XPathConstants.BOOLEAN);
    }

    public static String evalXPathString(String xpath, InputSource source) throws XPathExpressionException {
        return (String) newXPath(xpath).evaluate(source, XPathConstants.STRING);
    }

    public static String evalXPathString(String xpath, Node source) throws XPathExpressionException {
        return (String) newXPath(xpath).evaluate(source, XPathConstants.STRING);
    }

    public static List<Node> evalXPathNodes(String xpath, InputSource source) throws XPathExpressionException {
        return XmlUtils.toList((NodeList) newXPath(xpath).evaluate(source, XPathConstants.NODESET));
    }

    public static List<Node> evalXPathNodes(String xpath, Node source) throws XPathExpressionException {
        return XmlUtils.toList((NodeList) newXPath(xpath).evaluate(source, XPathConstants.NODESET));
    }

    public static List<Element> evalXPathElements(String xpath, InputSource source) throws XPathExpressionException {
        return XmlUtils.toElementList((NodeList) newXPath(xpath).evaluate(source, XPathConstants.NODESET));
    }

    public static List<Element> evalXPathElements(String xpath, Node source) throws XPathExpressionException {
        return XmlUtils.toElementList((NodeList) newXPath(xpath).evaluate(source, XPathConstants.NODESET));
    }

    public static List<String> evalXPathTextElements(String xpath, InputSource source) throws XPathExpressionException {
        return toTextContent(evalXPathNodes(xpath, source));
    }

    public static List<String> evalXPathTextElements(String xpath, Node source) throws XPathExpressionException {
        return toTextContent(evalXPathNodes(xpath, source));
    }

    public static List<String> toTextContent(List<Node> nodes) {
        ArrayList<String> list = new ArrayList<String>(nodes.size());
        for (Node node : nodes) {
            list.add(node.getTextContent());
        }
        return list;
    }

    private <X> X getFromCache(WeakHashMap<Thread, SoftReference<X>> cache) {
        final Thread t = Thread.currentThread();
        final SoftReference<X> ref = cache.get(t);
        if (ref != null) {
            return ref.get();
        }
        return null;
    }

    private <X> void cache(WeakHashMap<Thread, SoftReference<X>> cache, X value) {
        final Thread t = Thread.currentThread();
        cache.put(t, new SoftReference<X>(value));
    }
}
