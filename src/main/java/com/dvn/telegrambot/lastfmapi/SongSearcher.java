package com.dvn.telegrambot.lastfmapi;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Random;

public class SongSearcher {

    public Song findSong(String message) {

        String url = getSearchURL(message);
        String xmlResponse = getRequest(url);
        XmlSongParser songParser = new XmlSongParser();
        songParser.parseXMLResponse(xmlResponse);
        Song song = getSongForAnswer(); //Randomly chooses a song from the list of the GET request from the LastFM API
        System.out.println("Offered song " + song);
        System.out.println("--------------------------------------------");
        return song;
    }

    private static String getSearchURL(String phraseForSearch){
        return "http://ws.audioscrobbler.com/2.0/?method=track.search&track="
                + URLEncoder.encode(phraseForSearch, StandardCharsets.UTF_8)
                + "&api_key=bcca506c2c40c9c86bb8c24ee14596ec&limit=10";
    }

    private static String getRequest(String searchURL) {
        URL url = null;
        try {
            url = new URL(searchURL);
        } catch (MalformedURLException e) {
            System.out.println(e.getStackTrace());
        }

        HttpURLConnection connection = sendRequestToLastFM(url);

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

    private static HttpURLConnection sendRequestToLastFM(URL url) {
        HttpURLConnection connection = null;
        try {
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
        } catch (IOException e) {
            System.out.println(e.getStackTrace());
        }
        return connection;
    }
    private Song getSongForAnswer() {
        Random random = new Random();
        return XmlSongParser.songs.get(random.nextInt(XmlSongParser.songs.size()));
    }

}
