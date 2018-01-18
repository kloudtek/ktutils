/*
 * Copyright (c) 2014 Kloudtek Ltd
 */

package com.kloudtek.util.xml;

import org.apache.commons.io.FileUtils;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import org.w3c.dom.Document;
import org.w3c.dom.Text;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import java.io.File;
import java.io.IOException;
import java.io.StringReader;

import static com.kloudtek.util.xml.XPathUtils.evalXPathElement;
import static com.kloudtek.util.xml.XPathUtils.evalXPathNode;
import static com.kloudtek.util.xml.XmlUtils.getXPath;
import static com.kloudtek.util.xml.XmlUtils.parse;
import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertTrue;

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
        doc = parse(getClass().getResourceAsStream("/test.xml"));
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

    @Test
    public void testXXEAttackWithoutProtection() throws ParserConfigurationException, IOException, SAXException, XPathExpressionException {
        File tempFile = File.createTempFile("xxe", "test");
        try {
            FileUtils.write(tempFile, "hacked");
            String XXE_ATTACK = "<?xml version=\"1.0\" encoding=\"ISO-8859-1\"?>\n" +
                    " <!DOCTYPE foo [  \n" +
                    "  <!ELEMENT foo ANY >\n" +
                    "  <!ENTITY xxe SYSTEM \"" + tempFile.toURI() + "\" >]><foo>&xxe;</foo>";
            InputSource source = new InputSource(new StringReader(XXE_ATTACK));
            Document doc = null;
            try {
                doc = XmlUtils.getDocumentBuilderFactory(true, true).newDocumentBuilder().parse(source);
            } catch (SAXException e) {
                if (e.getMessage().contains("DOCTYPE is disallowed")) {
                    return;
                }
                throw e;
            }
            String val = XPathUtils.evalXPathString("foo/text()", doc);
            assertTrue(!val.trim().toLowerCase().contains("hacked"));
        } finally {
            tempFile.delete();
        }
    }
}
