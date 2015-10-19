package com.kiki.android.Utils;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class DomParser {

    private Document document;

    public DomParser(InputStream input) throws SAXException, IOException, ParserConfigurationException {
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
    }

    public DomParser(String data) throws SAXException, IOException, ParserConfigurationException {
        InputSource input = new InputSource(new StringReader(data));
        document = DocumentBuilderFactory.newInstance().newDocumentBuilder().parse(input);
    }

    // 완료
    public void getListValueParser(String tag) {
        List<String> list = new ArrayList<String>();
        NodeList nodes = document.getElementsByTagName(tag);
        for (int i = 0; i < nodes.getLength(); i++) {
            Node node = nodes.item(i);
            list.add(node.getTextContent());
        }
        // System.out.println( "@@ list : " + list.toString() );
    }

    public void getObjectParser(String tag) {
        Map<String, Object> map = new HashMap<String, Object>();
        Node node = document.getElementsByTagName(tag).item(0);
        findChildNodes(map, (Element) node);
        // System.out.println( "@@ map : " + map.toString() );
    }

    public String getValueParser(String tag) {
//        Map<String, Object> map = new HashMap<String, Object>();
        Node node = document.getElementsByTagName(tag).item(0);
//        System.out.println(node.getTextContent());
        return node.getTextContent();
    }

    public List<Map<String, Object>> getListObjectParser(String tag) {
        List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
        NodeList nodelist = document.getElementsByTagName(tag);
        for (int i = 0, n = nodelist.getLength(); i < n; i++) {
            Node node = nodelist.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Map<String, Object> map = new HashMap<String, Object>();
                list.add(map);
                findChildNodes(map, (Element) node);
            }
        }

//        for (Map<String, Object> map : list) {
//             System.out.println("@@ " + map.toString());
//        }
        return list;
        // System.out.println( "@@ list : " + list.toString() );
    }

    public void getAttriBute(String tag) {
        Map<String, Object> map = new HashMap<String, Object>();
        Node node = document.getElementsByTagName(tag).item(0);
        setNodeAttribute(map, (Element) node);
        // System.out.println("@@ attribute : " + map.toString());
    }

    private void findChildNodes(Map<String, Object> map, Element element) {

        if (element.hasAttributes()) {
            setNodeAttribute(map, element);
        }

        NodeList list = element.getChildNodes();
        // System.out.println("parent node name : " + element.getNodeName() +
        // ", list size : " + list.getLength());
        for (int i = 0; i < list.getLength(); i++) {
            Node node = list.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {
                // System.out.println("node Name : " + node.getNodeName() +
                // ", node type : " + node.getNodeType() + ", child cnt : "
                // + ((Element) node).getChildNodes().getLength());
                if (((Element) node).getChildNodes().getLength() == 1) {
                    map.put(node.getNodeName(), node.getTextContent());
                } else {
                    Map<String, Object> childMap = new HashMap<String, Object>();
                    map.put(node.getNodeName(), childMap);
                    findChildNodes(childMap, (Element) node);
                }

            }
        }
    }

    private void setNodeAttribute(Map<String, Object> map, Element element) {
        Map<String, String> mapattri = new HashMap<String, String>();
        map.put("attribute", mapattri);
        NamedNodeMap attrs = element.getAttributes();
        for (int i = 0; i < attrs.getLength(); i++) {
            Attr attribute = (Attr) attrs.item(i);
            mapattri.put(attribute.getName(), attribute.getValue());
        }
    }

    public static void main(String[] args) {
        try {
            DomParser parser = new DomParser(new FileInputStream("test5.xml"));
            parser.getListObjectParser("pathList");
            parser.getValueParser("price");
            parser.getValueParser("totalTime");
            parser.getValueParser("transferNum");
            // parser.getListParser( "url" );
            // parser.getObjectParser( "fra" );
            // parser.getListObjectParser( "fra" );
            // parser.getValueParser( "pid" );
            // parser.getAttriBute( "POPWORLD_CON" );
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        }
    }
}
