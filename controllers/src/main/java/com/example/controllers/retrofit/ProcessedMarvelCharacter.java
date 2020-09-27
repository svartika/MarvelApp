package com.example.controllers.retrofit;

import com.example.entitiy.models.MarvelCharacter;

import java.util.List;

public class ProcessedMarvelCharacter {
    int id;
    public String name, imageurl, description, modified;
    List<MarvelCharacter.URL> urls;

    MarvelCharacter.Collection comics, series, stories, events;

    public ProcessedMarvelCharacter(MarvelCharacter character) {
        id = character.id;
        name = character.name;
        imageurl = character.thumbnail.path.concat(character.thumbnail.extension);
        description = character.description;
        comics = character.comics;
        series = character.series;
        stories = character.stories;
        events = character.events;
        urls = character.urls;
        modified = character.modified;
    }
}
