package com.example.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelCharacter {
    @SerializedName("id")
    public int id;
    @SerializedName("name")
    public String name;
    @SerializedName("description")
    public String description;
    @SerializedName("modified")
    public String modified;
    @SerializedName("thumbnail")
    public Thumbnail thumbnail;
    public static class Thumbnail {
        @SerializedName("path")
        public String path;
        @SerializedName("extension")
        public String extension;
    }
    @SerializedName("comics")
    public Collection comics;

    @SerializedName("series")
    public Collection series;

    @SerializedName("stories")
    public Collection stories;

    @SerializedName("events")
    public Collection events;

    @SerializedName("urls")
    public List<URL> urls;
    public static class URL {
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
    }

    @SerializedName("resourceURI")
    public String resourceURI;

}
