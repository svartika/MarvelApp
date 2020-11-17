package com.example.controllers.commons;

import com.example.entitiy.models.MarvelComic;

import java.util.List;

public class ProcessedMarvelComic {
    public int id, issueNumber;
    public String title, variantDescription, description, imageurl, modified;
    public List<MarvelComic.URL> urls;
    public List<MarvelComic.Price> prices;
    public List<MarvelComic.Thumbnail> images;
    public ProcessedCollection creators, characters, stories;

    public ProcessedMarvelComic(MarvelComic marvelComic) {
        id = marvelComic.id;
        issueNumber = marvelComic.issueNumber;
        title = marvelComic.title;
        variantDescription = marvelComic.variantDescription;
        description = marvelComic.description;
        imageurl = marvelComic.thumbnail.path.concat(".").concat(marvelComic.thumbnail.extension);
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

