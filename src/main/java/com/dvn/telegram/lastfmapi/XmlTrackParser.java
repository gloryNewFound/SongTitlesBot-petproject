package com.dvn.telegram.lastfmapi;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.ArrayList;

public class XmlTrackParser {

    private static ArrayList<Track> tracks = new ArrayList<>();

    public void parseXMLResponse(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();

        XMLHandler handler = new XMLHandler();
        parser.parse(xmlString, handler);

        for (Track track: tracks) {
            System.out.println(track.toString());
        }
    }

}
