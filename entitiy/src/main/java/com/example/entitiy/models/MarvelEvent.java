package com.example.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelEvent {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("resourceURI")
    public String resourceURI;
    @SerializedName("urls")
    public List<MarvelEvent.URL> urls;
    public static class URL {
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
    }
    @SerializedName("modified")
    public String modified;
    @SerializedName("start")
    public String start;
    @SerializedName("end")
    public String end;
    @SerializedName("thumbnail")
    public MarvelEvent.Thumbnail thumbnail;
    public static class Thumbnail {
        @SerializedName("path")
        public String path;
        @SerializedName("extension")
        public String extension;
    }
    @SerializedName("creators")
    public Collection creators;
    @SerializedName("characters")
    public Collection characters;
    @SerializedName("stories")
    public Collection stories;
    @SerializedName("comics")
    public Collection comics;
    @SerializedName("series")
    public Collection series;
    @SerializedName("next")
    public nextPrev next;
    public static class nextPrev {
        @SerializedName("resourceURI")
        public String resourceURI;
        @SerializedName("name")
        public String name;
    }
    @SerializedName("previous")
    public nextPrev previous;
}
