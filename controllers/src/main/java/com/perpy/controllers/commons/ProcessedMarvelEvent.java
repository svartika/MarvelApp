package com.perpy.controllers.commons;

import com.perpy.entitiy.models.MarvelEvent;

import java.util.List;

public class ProcessedMarvelEvent extends ProcessedMarvelItemBase {
    public String resourceURI, modified, start, end;
    public ProcessedCollection creators, characters, stories, comics, series;
    public List<MarvelEvent.URL> urls;
    public MarvelEvent.nextPrev next, previous;

    public ProcessedMarvelEvent(MarvelEvent marvelEvent) {
        super();
        id = marvelEvent.id;
        title = marvelEvent.title;
        description = marvelEvent.description;
        resourceURI = marvelEvent.resourceURI;
        urls = marvelEvent.urls;
        modified = marvelEvent.modified;
        start = marvelEvent.start;
        end = marvelEvent.end;
        if(marvelEvent.thumbnail!=null) imageurl = marvelEvent.thumbnail.path.concat(".").concat(marvelEvent.thumbnail.extension);
        else imageurl = "";
        creators = new ProcessedCollection();
        creators.set(marvelEvent.creators);
        characters = new ProcessedCollection();
        characters.set(marvelEvent.characters);
        stories = new ProcessedCollection();
        stories.set(marvelEvent.stories);
        comics = new ProcessedCollection();
        comics.set(marvelEvent.comics);
        series = new ProcessedCollection();
        series.set(marvelEvent.series);
        next = marvelEvent.next;
        previous = marvelEvent.previous;
    }
}

