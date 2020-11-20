package com.example.controllers.commons;

public class ProcessedMarvelItemBase {
    public int id;
    public String title, description, modified, imageurl;
    public boolean displayName() {
        if(imageurl=="" || imageurl.contains("image_not_available"))
            return true;
        else
            return false;
    }
}
