/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xml.sax.SAXException;

import javax.xml.xpath.XPathExpressionException;
import java.io.IOException;

import static com.kloudtek.util.XPathUtils.evalXPathElement;
import static com.kloudtek.util.XPathUtils.evalXPathNode;
import static com.kloudtek.util.XmlUtils.getXPath;
import static com.kloudtek.util.XmlUtils.parse;
import static org.testng.Assert.assertEquals;

/**
 * Test @{link XmlUtils} functionality
 */
public class XmlUtilsTests {
    private static final String COW1 = "/*[name()='blob'][1]/*[name()='cow'][1]";
    private static final String COW2 = "/*[name()='blob'][1]/*[name()='cow'][2]";
    private static final String GOO = "/*[name()='blob'][1]/*[name()='b:goo'][2]";
    private static final String BIRD = "/*[name()='blob'][1]/*[name()='cow'][2]/@bird";
    private static final String TEXT1 = "/*[name()='blob'][1]/*[name()='cow'][2]/text()[1]";
    private static final String TEXT2 = "/*[name()='blob'][1]/*[name()='cow'][2]/text()[2]";
    private Document doc;

    @BeforeClass
    public void loadDoc() throws IOException, SAXException {
        doc = parse(getClass().getResourceAsStream("test.xml"));
    }

    @Test
    public void testGetXpathElement() throws IOException, SAXException, XPathExpressionException {
        assertEquals(getXPath(evalXPathElement(COW1, doc)), COW1);
        assertEquals(getXPath(evalXPathElement(COW2, doc)), COW2);
    }

    @Test
    public void testGetXpathElementNS() throws IOException, SAXException, XPathExpressionException {
        assertEquals(getXPath(evalXPathElement(GOO, doc)), GOO);
    }

    @Test
    public void testGetXpathAttribute() throws IOException, SAXException, XPathExpressionException {
        assertEquals(getXPath(evalXPathNode(BIRD, doc)), BIRD);
    }

    @Test
    public void testGetXpathText() throws IOException, SAXException, XPathExpressionException {
        final Text node1 = (Text) evalXPathNode(TEXT1, doc);
        assertEquals(node1.getNodeValue().trim(), "river");
        assertEquals(getXPath(node1), TEXT1);
        final Text node2 = (Text) evalXPathNode(TEXT2, doc);
        assertEquals(node2.getNodeValue().trim(), "cat");
        assertEquals(getXPath(node2), TEXT2);
    }
}
