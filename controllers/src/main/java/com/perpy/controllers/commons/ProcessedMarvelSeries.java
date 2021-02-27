package com.perpy.controllers.commons;

import com.perpy.entitiy.models.MarvelSeries;

import java.util.List;

public class ProcessedMarvelSeries extends ProcessedMarvelItemBase {
    public int startYear, endYear;
    public String resourceURI, rating, type, modified;
    public ProcessedCollection creators, characters, stories, comics, events;
    public List<MarvelSeries.URL> urls;

    public ProcessedMarvelSeries(MarvelSeries marvelSeries) {
        super();
        id = marvelSeries.id;
        title = marvelSeries.title;
        description = marvelSeries.description;
        resourceURI = marvelSeries.resourceURI;
        urls = marvelSeries.urls;
        startYear = marvelSeries.startYear;
        endYear = marvelSeries.endYear;
        rating = marvelSeries.rating;
        type = marvelSeries.type;
        modified = marvelSeries.modified;
        if(marvelSeries.thumbnail!=null) imageurl = marvelSeries.thumbnail.path.concat(".").concat(marvelSeries.thumbnail.extension);
        else imageurl ="";
        creators = new ProcessedCollection();
        creators.set(marvelSeries.creators);
        characters = new ProcessedCollection();
        characters.set(marvelSeries.characters);
        stories = new ProcessedCollection();
        stories.set(marvelSeries.stories);
        comics = new ProcessedCollection();
        comics.set(marvelSeries.comics);
        events = new ProcessedCollection();
        events.set(marvelSeries.events);
    }
}

