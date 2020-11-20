package com.example.controllers.commons;

import com.example.entitiy.models.MarvelStory;

import java.util.List;

public class ProcessedMarvelStory extends ProcessedMarvelItemBase {
    public String resourceURI, type, modified;
    public ProcessedCollection creators, characters, series, comics, events;
    public MarvelStory.OriginalIssue originalIssues;

    public ProcessedMarvelStory(MarvelStory marvelStory) {
        id = marvelStory.id;
        title = marvelStory.title;
        description = marvelStory.description;
        resourceURI = marvelStory.resourceURI;
        type = marvelStory.type;
        modified = marvelStory.modified;
        if(marvelStory.thumbnail!=null) imageurl = marvelStory.thumbnail.path.concat(".").concat(marvelStory.thumbnail.extension);
        else imageurl = "";
        creators = new ProcessedCollection();
        creators.set(marvelStory.creators);
        characters = new ProcessedCollection();
        characters.set(marvelStory.characters);
        series = new ProcessedCollection();
        series.set(marvelStory.series);
        comics = new ProcessedCollection();
        comics.set(marvelStory.comics);
        events = new ProcessedCollection();
        events.set(marvelStory.events);
        originalIssues = marvelStory.originalIssue;
    }
}

