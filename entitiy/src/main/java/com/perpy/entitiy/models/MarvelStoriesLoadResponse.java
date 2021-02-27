package com.perpy.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class MarvelStoriesLoadResponse {
    @SerializedName("code")
    int code;
    @SerializedName("status")
    String status;
    @SerializedName("copyright")
    String copyright;
    @SerializedName("attributionText")
    String attributionText;
    @SerializedName("etag")
    String etag;
    @SerializedName("data")
    public Data data;

    public static class Data {
        @SerializedName("offset")
        int offset;
        @SerializedName("limit")
        int limit;
        @SerializedName("total")
        int total;
        @SerializedName("count")
        int count;
        @SerializedName("results")
        public List<MarvelStory> stories;

    }
}
