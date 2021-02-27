package com.perpy.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelComic {
    @SerializedName("id")
    public int id;
    @SerializedName("digitalId")
    public int digitalId;
    @SerializedName("title")
    public String title;
    @SerializedName("issueNumber")
    public int issueNumber;
    @SerializedName("variantDescription")
    public String variantDescription;
    @SerializedName("description")
    public String description;
    @SerializedName("modified")
    public String modified;
    @SerializedName("isbn")
    public String isbn;
    @SerializedName("upc")
    public String upc;
    @SerializedName("diamondCode")
    public String diamondCode;
    @SerializedName("ean")
    public String ean;
    @SerializedName("issn")
    public String issn;
    @SerializedName("format")
    public String format;
    @SerializedName("pageCount")
    public int pageCount;
    @SerializedName("textObjects")
    public List<MarvelComic.TextObjects> textObjects;
    public static class TextObjects {
        @SerializedName("type")
        public String type;
        @SerializedName("language")
        public String language;
        @SerializedName("text")
        public String text;
    }
    @SerializedName("resourceURI")
    public String resourceURI;
    @SerializedName("urls")
    public List<MarvelComic.URL> urls;
    public static class URL {
        @SerializedName("type")
        public String type;
        @SerializedName("url")
        public String url;
    }
    public MarvelComic.Series series;
    public static class Series {
        @SerializedName("resourceURI")
        public String resourceURI;
        @SerializedName("name")
        public String name;
    }
    @SerializedName("variants")
    public List<MarvelComic.Variant> variants;
    public static class Variant {
    }
    @SerializedName("collections")
    public List<MarvelComic.Collections> collections;
    public static class Collections{
    }
    @SerializedName("collectedIssues")
    public List<MarvelComic.CollectedIssue> collectedIssues;
    public static class CollectedIssue {
    }
    @SerializedName("dates")
    public List<MarvelComic.Date> dates;
    public static class Date {
        @SerializedName("type")
        public String type;
        @SerializedName("date")
        public String date;
    }
    @SerializedName("prices")
    public List<MarvelComic.Price> prices;
    public static class Price {
        @SerializedName("type")
        public String type;
        @SerializedName("price")
        public String price;
    }
    @SerializedName("thumbnail")
    public MarvelComic.Thumbnail thumbnail;
    public static class Thumbnail {
        @SerializedName("path")
        public String path;
        @SerializedName("extension")
        public String extension;
    }
    @SerializedName("images")
    public List<MarvelComic.Thumbnail> images;

    @SerializedName("creators")
    public Collection creators;
    @SerializedName("characters")
    public Collection characters;
    @SerializedName("stories")
    public Collection stories;
    @SerializedName("events")
    public Collection events;


}
