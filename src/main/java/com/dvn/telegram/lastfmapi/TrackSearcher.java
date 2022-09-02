package com.dvn.telegram.lastfmapi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class TrackSearcher {

    public static void main(String[] args) throws IOException {
        TrackSearcher searcher = new TrackSearcher();
        String url = searcher.getSearchURL("Stairway to heaven");
        String xmlResponse = searcher.getRequest(url);
        System.out.println(xmlResponse);
    }

    public String getSearchURL(String word){
        return "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + URLEncoder.encode(word, StandardCharsets.UTF_8)
                + "&api_key=bcca506c2c40c9c86bb8c24ee14596ec";
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
        return response.toString();
    }

}
