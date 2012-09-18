/*
 * Copyright (c) 2011. KloudTek Ltd
 */

package com.kloudtek.util;

import org.w3c.dom.*;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSOutput;
import org.w3c.dom.ls.LSResourceResolver;
import org.w3c.dom.ls.LSSerializer;
import org.w3c.dom.traversal.DocumentTraversal;
import org.w3c.dom.traversal.NodeFilter;
import org.w3c.dom.traversal.TreeWalker;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import java.io.*;
import java.lang.ref.SoftReference;
import java.net.URI;
import java.util.*;

// TODO: implement caches

public class XmlUtils {
    private static final WeakHashMap<Thread, SoftReference<DocumentBuilderFactory>> documentBuilderFactoryCache = new WeakHashMap<Thread, SoftReference<DocumentBuilderFactory>>();
    private static final WeakHashMap<Thread, SoftReference<DocumentBuilder>> documentBuilderCache = new WeakHashMap<Thread, SoftReference<DocumentBuilder>>();
    private static final WeakHashMap<Thread, SoftReference<DOMImplementationRegistry>> domImplReg = new WeakHashMap<Thread, SoftReference<DOMImplementationRegistry>>();

    public static Document createDocument() {
        return createDocument(true);
    }

    public static Document createDocument(boolean namespaceAware) {
        return getDocumentBuilder(namespaceAware).newDocument();
    }

    private static Schema createSchema(LSResourceResolver rsResolver, final InputStream schema) throws SAXException {
        return createSchema(rsResolver, new StreamSource(schema));
    }

    private static Schema createSchema(final InputStream schema) throws SAXException {
        return createSchema(new StreamSource(schema));
    }

    private static Schema createSchema(LSResourceResolver rsResolver, final Reader schema) throws SAXException {
        return createSchema(rsResolver, new StreamSource(schema));
    }

    public static Schema createSchema(final Reader schema) throws SAXException {
        return createSchema(new StreamSource(schema));
    }

    public static Schema createSchema(Source... sources) throws SAXException {
        return createSchema(null, sources);
    }

    public static Schema createSchema(LSResourceResolver rsResolver, Source... sources) throws SAXException {
        final SchemaFactory sf = SchemaFactory.newInstance("http://www.w3.org/2001/XMLSchema");
        if (rsResolver != null) {
            sf.setResourceResolver(rsResolver);
        }
        if (sources.length == 0) {
            throw new IllegalArgumentException("At least one source must be provided");
        } else if (sources.length > 1) {
            return sf.newSchema(sources);
        } else {
            return sf.newSchema(sources[0]);
        }
    }

    public static Document parse(File file) throws IOException, SAXException {
        return getDocumentBuilder().parse(file);
    }

    public static Document parse(InputStream stream) throws IOException, SAXException {
        return getDocumentBuilder().parse(stream);
    }

    public static Document parse(Reader reader) throws IOException, SAXException {
        return getDocumentBuilder().parse(new InputSource(reader));
    }

    public static Document parse(String uri) throws IOException, SAXException {
        return getDocumentBuilder().parse(uri);
    }

    public static Document parse(URI uri) throws IOException, SAXException {
        return getDocumentBuilder().parse(uri.toString());
    }

    public static Node getChild(Node parent, String nodename) {
        for (Node child : toList(parent.getChildNodes())) {
            if (child.getNodeName().equalsIgnoreCase(nodename)) {
                return child;
            }
        }
        return null;
    }

    /**
     * Search for a child element, with the option to automatically create it if the create flag is true.
     *
     * @param parent      Parent node.
     * @param elementname Element name.
     * @param create      Flag that indicates if the element should be automatically created should it not exist.
     * @return Child element or null if it doesn't exist and create is false.
     * @throws DOMException If there is an node with the specified name, that isn't an element.
     */
    public static Element getChildElement(Node parent, String elementname, boolean create) throws DOMException {
        Node child = getChild(parent, elementname);
        if (child instanceof Element) {
            return (Element) child;
        } else if (child == null) {
            if (create) {
                child = parent.getOwnerDocument().createElement(elementname);
                parent.appendChild(child);
                return (Element) child;
            } else {
                return null;
            }
        } else {
            throw new DOMException(DOMException.TYPE_MISMATCH_ERR, "There are exists an non-element node (" + child.getClass().getName() + ") named " + elementname);
        }
    }

    public static void serialize(Node node, Writer writer) {
        doSerialize(node, writer, node instanceof Document, true);
    }

    public static void serialize(Node node, OutputStream stream) {
        doSerialize(node, stream, node instanceof Document, true);
    }

    public static void serialize(Node node, Writer writer, boolean xmlDeclaration, boolean prettyPrint) {
        doSerialize(node, writer, xmlDeclaration, prettyPrint);
    }

    public static void serialize(Node node, OutputStream writer, boolean xmlDeclaration, boolean prettyPrint) {
        doSerialize(node, writer, xmlDeclaration, prettyPrint);
    }

    public static String toString(Node node) {
        return toString(node, node instanceof Document, true);
    }

    public static String toString(Node node, boolean xmlDeclaration, boolean prettyPrint) {
        StringWriter xml = new StringWriter();
        doSerialize(node, xml, xmlDeclaration, prettyPrint);
        return xml.toString();
    }

    private static void doSerialize(Node node, Object writer, boolean xmlDeclaration, boolean prettyPrint) {
        DOMImplementationRegistry registry = getDomImplRegistry();
        final DOMImplementationLS impl = (DOMImplementationLS) registry.getDOMImplementation("LS");
        final LSSerializer serializer = impl.createLSSerializer();
        final DOMConfiguration cfg = serializer.getDomConfig();
        cfg.setParameter("xml-declaration", xmlDeclaration);
        cfg.setParameter("format-pretty-print", prettyPrint);
        final LSOutput output = impl.createLSOutput();
        if (writer instanceof Writer) {
            output.setCharacterStream((Writer) writer);
        } else if (writer instanceof OutputStream) {
            output.setByteStream((OutputStream) writer);
        } else {
            throw new IllegalArgumentException("Invalid writer: " + writer.getClass().getName());
        }
        serializer.write(node, output);
    }

    public static DOMImplementationRegistry getDomImplRegistry() {
        try {
            return DOMImplementationRegistry.newInstance();
        } catch (ClassNotFoundException e) {
            throw new UnexpectedException(e);
        } catch (InstantiationException e) {
            throw new UnexpectedException(e);
        } catch (IllegalAccessException e) {
            throw new UnexpectedException(e);
        }
    }

    public static DocumentBuilderFactory getDocumentBuilderFactory(final boolean namespaceAware) {
        final DocumentBuilderFactory documentBuilderFactory = DocumentBuilderFactory.newInstance();
        documentBuilderFactory.setNamespaceAware(namespaceAware);
        return documentBuilderFactory;
    }

    public static DocumentBuilder getDocumentBuilder() {
        return getDocumentBuilder(true);
    }

    public static DocumentBuilder getDocumentBuilder(boolean namespaceAware) {
        try {
            return getDocumentBuilderFactory(namespaceAware).newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new UnexpectedException(e);
        }
    }

    /**
     * Return an xpath expression that matches the provided node.
     *
     * @param node Node.
     * @return resolved expression.
     */
    public static String getXPath(Node node) {
        List<String> list = new LinkedList<String>();
        while (node != null) {
            if (node.getNodeType() == Node.ATTRIBUTE_NODE) {
                list.add("@" + node.getNodeName());
                node = ((Attr) node).getOwnerElement();
            } else if (node.getNodeType() == Node.ELEMENT_NODE) {
                int idx = getSiblingNumber(node);
                list.add("*[name()='" + node.getNodeName() + "'][" + idx + "]");
                node = node.getParentNode();
            } else if (node.getNodeType() == Node.TEXT_NODE) {
                int idx = getSiblingNumber(node);
                list.add("text()" + "[" + idx + "]");
                node = node.getParentNode();
            } else if (node.getNodeType() == Node.DOCUMENT_NODE) {
                node = null;
            } else {
                throw new IllegalArgumentException("Unsupported node type: " + node.getNodeType() + " / " + node.getClass().getName());
            }

        }
        StringBuilder xpath = new StringBuilder();
        while (!list.isEmpty()) {
            xpath.append("/").append(list.remove(list.size() - 1));
        }
        return xpath.toString();
    }

    /**
     * Calculate how many previous siblings of the same node type exists, and return that value plus one.
     *
     * @param node Node
     * @return Sibling number.
     */
    public static int getSiblingNumber(Node node) {
        int idx = 1;
        for (Node s = node.getPreviousSibling(); s != null; s = s.getPreviousSibling()) {
            if (s.getNodeType() == node.getNodeType() && s.getNodeName().equals(node.getNodeName())) {
                idx++;
            }
        }
        return idx;
    }

    @SuppressWarnings({"unchecked"})
    public static <X extends Node> List<X> getChildNodes(Node node, Class<X> type) {
        final NodeList nodelist = node.getChildNodes();
        ArrayList<X> list = new ArrayList<X>(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            final Node child = nodelist.item(i);
            if (type.isInstance(child)) {
                list.add((X) child);
            }
        }
        return list;
    }

    /**
     * Convert a {@link NodeList} to a {@link List}
     *
     * @param nodelist Node list
     * @return {@link List} of nodes
     */
    public static List<Node> toList(NodeList nodelist) {
        ArrayList<Node> list = new ArrayList<Node>(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            list.add(nodelist.item(i));
        }
        return list;
    }

    /**
     * Convert a {@link NamedNodeMap} contains attribute nodes {@link Attr}
     *
     * @param attrList {@link NamedNodeMap} containing {@link Attr} objects exclusively
     * @return List of {@link Attr}.
     */
    public static List<Attr> toList(NamedNodeMap attrList) {
        ArrayList<Attr> list = new ArrayList<Attr>(attrList.getLength());
        for (int i = 0; i < attrList.getLength(); i++) {
            list.add((Attr) attrList.item(i));
        }
        return list;
    }

    /**
     * Clone all the nodes contains in the provided node list, and return them in a {@link List}.
     *
     * @param nodelist Node list
     * @param deep     If true, recursively clone the subtree under each node; if false, clone only the node itself (and its attributes, if it is an Element).
     * @return {@link List} of cloned nodes
     */
    public static List<Node> toClonedList(NodeList nodelist, boolean deep) {
        ArrayList<Node> list = new ArrayList<Node>(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            list.add(nodelist.item(i).cloneNode(deep));
        }
        return list;
    }

    /**
     * Clone all the nodes contains in the provided node list, assuming they are of type {@link Element}, and return them in a {@link List}.
     *
     * @param nodelist Node list
     * @return {@link List} of cloned nodes
     */
    public static List<Element> toElementList(NodeList nodelist) {
        ArrayList<Element> list = new ArrayList<Element>(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            list.add((Element) nodelist.item(i));
        }
        return list;
    }

    /**
     * Clone all the nodes contains in the provided node list, assuming they are of type {@link Element}, and return them in a {@link List}.
     *
     * @param nodelist Node list
     * @param deep     If true, recursively clone the subtree under each node; if false, clone only the node itself (and its attributes, if it is an Element).
     * @return {@link List} of cloned nodes
     */
    public static List<Element> toClonedElementList(NodeList nodelist, boolean deep) {
        ArrayList<Element> list = new ArrayList<Element>(nodelist.getLength());
        for (int i = 0; i < nodelist.getLength(); i++) {
            list.add((Element) nodelist.item(i).cloneNode(deep));
        }
        return list;
    }

    public static Element createElement(final String name, final Node parent, final Object... attrs) {
        final Document doc = parent instanceof Document ? (Document) parent : parent.getOwnerDocument();
        Element element = doc.createElement(name);
        setAttributes(element, attrs);
        parent.appendChild(element);
        return element;
    }

    public static Element createNSElement(final String name, final Node parent, final Object... attrs) {
        return createNSElement(name, null, null, parent, attrs);
    }

    public static Element createNSElement(final String name, final String namespace, String prefix, final Node parent, final Object... attrs) {
        final Document doc = parent instanceof Document ? (Document) parent : parent.getOwnerDocument();
        final Element element = doc.createElementNS(namespace != null ? namespace : parent.getNamespaceURI(), name);
        if (prefix != null) {
            element.setPrefix(prefix);
        } else if (parent.getPrefix() != null) {
            element.setPrefix(parent.getPrefix());
        }
        setAttributes(element, attrs);
        parent.appendChild(element);
        return element;
    }

    public static void setAttributes(Element element, Object... attrs) {
        if (attrs.length % 2 != 0) {
            throw new IllegalArgumentException("Invalid number of attributes");
        }
        for (int i = 0; i < attrs.length; i += 2) {
            element.setAttribute(attrs[i].toString(), attrs[i + 1].toString());
        }
    }

    /**
     * As calling {@link #createTreeWalker(org.w3c.dom.Node, int, org.w3c.dom.traversal.NodeFilter, boolean)} with
     * nodeFilter=null, entityReferenceExpansion=true }
     *
     * @param node       The node which will serve as the root for the TreeWalker. The whatToShow flags and the NodeFilter are not considered when setting this value; any node type will be accepted as the root. The currentNode of the TreeWalker is initialized to this node, whether or not it is visible. The root functions as a stopping point for traversal methods that look upward in the document structure, such as parentNode and nextNode. The root must not be null.
     * @param whatToShow This flag specifies which node types may appear in the logical view of the tree presented by the TreeWalker. See the description of NodeFilter for the set of possible SHOW_ values.These flags can be combined using OR.
     * @return TreeWalker
     * @see {@link #createTreeWalker(org.w3c.dom.Node, int, org.w3c.dom.traversal.NodeFilter, boolean)}
     */
    public static TreeWalker createTreeWalker(Node node, int whatToShow) {
        return createTreeWalker(node, whatToShow, null, true);
    }

    public static TreeWalker createTreeWalker(Node node, int whatToShow, NodeFilter nodeFilter, boolean entityReferenceExpansion) {
        final DocumentTraversal documentTraversal;
        if (node instanceof Document) {
            documentTraversal = (DocumentTraversal) node;
        } else {
            documentTraversal = (DocumentTraversal) node.getOwnerDocument();
        }
        return documentTraversal.createTreeWalker(node, whatToShow, nodeFilter, entityReferenceExpansion);
    }

    public static TreeWalker createElementTreeWalker(Node node) {
        return createElementTreeWalker(node, true);
    }

    public static TreeWalker createElementTreeWalker(Node node, boolean entityReferenceExpansion) {
        return createElementTreeWalker(node, null, entityReferenceExpansion);
    }

    public static TreeWalker createElementTreeWalker(Node node, NodeFilter nodeFilter, boolean entityReferenceExpansion) {
        return createTreeWalker(node, NodeFilter.SHOW_ELEMENT, nodeFilter, entityReferenceExpansion);
    }

    public static XMLGregorianCalendar convertDate(Date date) {
        try {
            Calendar c = new GregorianCalendar();
            c.setTime(date);
            XMLGregorianCalendar xmlGregorianCalendar = DatatypeFactory.newInstance().newXMLGregorianCalendar();
            xmlGregorianCalendar.setDay(c.get(Calendar.DAY_OF_MONTH));
            xmlGregorianCalendar.setMonth(c.get(Calendar.MONTH));
            xmlGregorianCalendar.setYear(c.get(Calendar.YEAR));
            return xmlGregorianCalendar;
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    public static Date convertDate(XMLGregorianCalendar date) {
        return new GregorianCalendar(date.getYear(), date.getMonth(), date.getDay()).getTime();
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
