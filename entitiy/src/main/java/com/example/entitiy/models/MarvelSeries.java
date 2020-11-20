package com.example.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelSeries {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("resourceURI")
    public String resourceURI;
    @SerializedName("urls")
    public List<MarvelSeries.URL> urls;
    public static class URL {
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
    }
    @SerializedName("startYear")
    public int startYear;
    @SerializedName("endYear")
    public int endYear;
    @SerializedName("rating")
    public String rating;
    @SerializedName("type")
    public String type;
    @SerializedName("modified")
    public String modified;
    @SerializedName("thumbnail")
    public MarvelSeries.Thumbnail thumbnail;
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
    @SerializedName("events")
    public Collection events;
}
