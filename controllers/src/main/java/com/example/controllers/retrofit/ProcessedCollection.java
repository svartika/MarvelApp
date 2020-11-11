package com.example.controllers.retrofit;

import com.example.entitiy.models.MarvelCharacter;

import java.util.ArrayList;
import java.util.List;

public class ProcessedCollection {
    public int available;
    public String collectionURI;
    public List<ProcessedItem> items;

    public void set(MarvelCharacter.Collection collection) {
        this.available = collection.available;
        this.collectionURI = collection.collectionURI;
        if(items==null) items = new ArrayList<>();
        if(collection!=null) {
            for (MarvelCharacter.Item item : collection.items) {
                this.items.add(new ProcessedItem(item));
            }
        }
    }

    public static class ProcessedItem {
        public String name;
        public ProcessedItem(MarvelCharacter.Item item) {
            this.name = item.name;
        }
    }
}
