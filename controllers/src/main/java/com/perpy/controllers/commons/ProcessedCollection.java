package com.perpy.controllers.commons;

import com.perpy.entitiy.models.Collection;

import java.util.ArrayList;
import java.util.List;

public class ProcessedCollection {
    public int available;
    public String collectionURI;
    public List<ProcessedItem> items;

    public void set(Collection collection) {
        this.available = collection.available;
        this.collectionURI = collection.collectionURI;
        if(items==null) items = new ArrayList<>();
        if(collection!=null) {
            for (Collection.Item item : collection.items) {
                this.items.add(new ProcessedItem(item));
            }
        }
    }

    public static class ProcessedItem {
        public String name;
        public ProcessedItem(Collection.Item item) {
            this.name = item.name;
        }
    }
}
