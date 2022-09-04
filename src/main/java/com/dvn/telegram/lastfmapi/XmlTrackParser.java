package com.dvn.telegram.lastfmapi;

import org.xml.sax.Attributes;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

public class XmlTrackParser {

    public static List<Track> tracks = new ArrayList<>();
    public void parseXMLResponse(String xmlString) throws ParserConfigurationException, SAXException, IOException {
        tracks.clear();
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        AdvancedXmlHandler handler = new AdvancedXmlHandler();
        XMLReader reader = parser.getXMLReader();
        reader.setContentHandler(handler);
        reader.parse(new InputSource(new StringReader(xmlString)));

        for (Track track: tracks) {
            System.out.println(track.toString());
        }

    }

    private static class AdvancedXmlHandler extends DefaultHandler {
        private String title;

        @Override
        public void startDocument() throws SAXException {
            //System.out.println("Start parsing");
        }

        @Override
        public void endDocument() throws SAXException {
            //System.out.println("Finish parsing");
        }

        private String artist;
        private int listeners = -1;
        private String currentElementName;

        @Override
        public void characters(char[] ch, int start, int length) {
            String tagText = new String(ch, start, length);

            if (tagText.contains("<") || currentElementName == null) {
                return;
            }

            //tagText = tagText.replace(" ", "").trim();

            if (currentElementName.equals("name")) {
                title = tagText;
            }
            if (currentElementName.equals("artist")) {
                artist = tagText;
            }
            if (currentElementName.equals("listeners")) {
                listeners = Integer.parseInt(tagText);
            }

        }

        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) {
            currentElementName = qName;
        }

        @Override
        public void endElement(String uri, String localName, String qName) {
            if ( (title != null && !title.isEmpty()) && (artist != null && !artist.isEmpty()) && (listeners >= 0) ) {
                tracks.add(new Track(title, artist, listeners));
                title = null;
                artist = null;
                listeners = -1;
            }
        }
    }

}
