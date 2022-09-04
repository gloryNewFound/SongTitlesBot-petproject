package com.dvn.telegram.lastfmapi;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrackSearcher {
    public static List<Track> tracks = new ArrayList<>();
    public static void main(String[] args) throws ParserConfigurationException, SAXException, IOException {
        TrackSearcher searcher = new TrackSearcher();
        String url = searcher.getSearchURL("All i want");
        String xmlResponse = null;
        try {
            xmlResponse = searcher.getRequest(url);
        } catch (IOException e) {
            System.out.println("Exception in response" + e.getStackTrace());
        }
        System.out.println(xmlResponse);
        XmlTrackParser trackParser = new XmlTrackParser();
        trackParser.parseXMLResponse(xmlResponse);

        System.out.println(tracks.size());
    }

    public String getSearchURL(String word){
        return "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + URLEncoder.encode(word, StandardCharsets.UTF_8)
                + "&api_key=bcca506c2c40c9c86bb8c24ee14596ec&limit=100";
    }

    public String getRequest(String searchURL) throws IOException {
        URL url = new URL(searchURL);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");

        StringBuffer response = new StringBuffer();
        try (BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()))) {
            while (input.ready()) {
                response.append(input.readLine());
            }
        }

        String finalResponse= response.toString();

        return finalResponse;
    }

}
