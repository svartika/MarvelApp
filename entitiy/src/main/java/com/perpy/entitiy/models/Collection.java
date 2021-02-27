package com.perpy.entitiy.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Collection {
    @SerializedName("available")
    public int available;
    @SerializedName("collectionURI")
    public String collectionURI;
    @SerializedName("items")
    public List<Item> items;

    public static class Item {
        @SerializedName("name")
        public String name;
    }
}
