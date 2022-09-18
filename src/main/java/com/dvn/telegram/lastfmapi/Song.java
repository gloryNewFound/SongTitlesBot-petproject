package com.dvn.telegram.lastfmapi;


public class Song {

    public String title;
    public String artist;
    public int listeners;

    public Song(String title, String artist, int listeners) {
        this.title = title;
        this.artist = artist;
        this.listeners = listeners;
    }

    @Override
    public String toString() {
        return "Track{" +
                "title='" + title + '\'' +
                ", artist='" + artist + '\'' +
                ", listeners=" + listeners +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getListeners() {
        return listeners;
    }

    public void setListeners(int listeners) {
        this.listeners = listeners;
    }
}
