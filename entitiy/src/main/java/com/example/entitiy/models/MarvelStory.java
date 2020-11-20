package com.example.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelStory {
    @SerializedName("id")
    public int id;
    @SerializedName("title")
    public String title;
    @SerializedName("description")
    public String description;
    @SerializedName("resourceURI")
    public String resourceURI;
    @SerializedName("type")
    public String type;
    @SerializedName("modified")
    public String modified;
    @SerializedName("thumbnail")
    public MarvelStory.Thumbnail thumbnail;
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
    @SerializedName("series")
    public Collection series;
    @SerializedName("comics")
    public Collection comics;
    @SerializedName("events")
    public Collection events;
    @SerializedName("originalIssue")
    public OriginalIssue originalIssue;
    public static class OriginalIssue {
        @SerializedName("resourceURI")
        public String resourceURI;
        @SerializedName("name")
        public String name;
    }
}
