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

    public Track findTrack(String message) throws ParserConfigurationException, SAXException, IOException {

        String url = this.getSearchURL(message);
        String xmlResponse = null;
        xmlResponse = this.getRequest(url);
        System.out.println(xmlResponse);
        XmlTrackParser trackParser = new XmlTrackParser();
        trackParser.parseXMLResponse(xmlResponse);
        Track track = XmlTrackParser.tracks.get(0);

        return track;
    }

    public String getSearchURL(String word){
        return "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + URLEncoder.encode(word, StandardCharsets.UTF_8)
                + "&api_key=bcca506c2c40c9c86bb8c24ee14596ec&limit=10";
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
        } catch (IOException e) {
            System.out.println("IO exception in getRequest" + e.getStackTrace());
        }

        String finalResponse= response.toString();

        return finalResponse;
    }

}
