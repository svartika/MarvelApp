package com.example.controllers.commons;

import com.example.entitiy.models.MarvelComic;

import java.util.List;

public class ProcessedMarvelComic extends ProcessedMarvelItemBase {
    public int issueNumber;
    public String variantDescription;
    public List<MarvelComic.Price> prices;
    public List<MarvelComic.Thumbnail> images;
    public ProcessedCollection creators, characters, stories;
    public List<MarvelComic.URL> urls;

    public ProcessedMarvelComic(MarvelComic marvelComic) {
        id = marvelComic.id;
        issueNumber = marvelComic.issueNumber;
        title = marvelComic.title;
        variantDescription = marvelComic.variantDescription;
        description = marvelComic.description;
        if(marvelComic.thumbnail!=null) imageurl = marvelComic.thumbnail.path.concat(".").concat(marvelComic.thumbnail.extension);
        else imageurl = "";
        modified = marvelComic.modified;
        urls = marvelComic.urls;
        prices = marvelComic.prices;
        images = marvelComic.images;
        creators = new ProcessedCollection();
        creators.set(marvelComic.creators);
        characters = new ProcessedCollection();
        characters.set(marvelComic.characters);
        stories = new ProcessedCollection();
        stories.set(marvelComic.stories);
    }


}

