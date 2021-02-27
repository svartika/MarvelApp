package com.perpy.controllers.eventdetail;

import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelSeries;
import com.perpy.entitiy.models.MarvelStory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class EventDetailNetworkInterface {
    public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersForEventRaw(int eventId);

    public Observable<List<ProcessedMarvelCharacter>> loadMarvelCharacterForEvent(int eventId) {
        return loadMarvelCharactersForEventRaw(eventId).map(charactersList -> {
            return processRawCharactersResponse(charactersList);
        });
    }

    List<ProcessedMarvelCharacter> processRawCharactersResponse(List<MarvelCharacter> charactersList) {
        List<ProcessedMarvelCharacter> processedMarvelCharacters = new ArrayList<>();
        for(MarvelCharacter marvelCharacter: charactersList) {
            ProcessedMarvelCharacter processedMarvelCharacter = new ProcessedMarvelCharacter(marvelCharacter);
            processedMarvelCharacters.add(processedMarvelCharacter);
        }
        return processedMarvelCharacters;
    }

    public abstract Observable<List<MarvelComic>> loadMarvelComicsForEventRaw(int eventId);

    public Observable<List<ProcessedMarvelComic>> loadMarvelComicForEvent(int eventId) {
        return loadMarvelComicsForEventRaw(eventId).map ( comicsList -> {
            return processRawComicsResponse(comicsList);
        });
    }

    List<ProcessedMarvelComic> processRawComicsResponse(List<MarvelComic> marvelComics) {
        List<ProcessedMarvelComic> processedMarvelComics = new ArrayList<>();
        for(MarvelComic marvelComic: marvelComics) {
            ProcessedMarvelComic processedMarvelComic = new ProcessedMarvelComic(marvelComic);
            processedMarvelComics.add(processedMarvelComic);
        }
        return processedMarvelComics;
    }

    public abstract Observable<List<MarvelSeries>> loadMarvelSeriesForEventRaw(int eventId);

    public Observable<List<ProcessedMarvelSeries>> loadMarvelSeriesForEvent(int eventId) {
        return loadMarvelSeriesForEventRaw(eventId).map( seriesList -> {
            return processRawSeriesResponse(seriesList);
        });
    }

    List<ProcessedMarvelSeries> processRawSeriesResponse(List<MarvelSeries> seriesList) {
        List<ProcessedMarvelSeries> processedMarvelSeriesList = new ArrayList<>();
        for(MarvelSeries series: seriesList) {
            ProcessedMarvelSeries processedMarvelSeries = new ProcessedMarvelSeries(series);
            processedMarvelSeriesList.add(processedMarvelSeries);
        }
        return processedMarvelSeriesList;
    }

    public abstract Observable<List<MarvelStory>> loadMarvelStoriesForEventRaw(int eventId);

    public Observable<List<ProcessedMarvelStory>> loadMarvelStoriesForEvent(int eventId) {
        return loadMarvelStoriesForEventRaw(eventId).map( storiesList -> {
            return processRawStoriesResponse(storiesList);
        });
    }

    List<ProcessedMarvelStory> processRawStoriesResponse(List<MarvelStory> marvelStories) {
        List<ProcessedMarvelStory> processedMarvelStories = new ArrayList<>();
        for(MarvelStory story: marvelStories) {
            ProcessedMarvelStory processedMarvelStory = new ProcessedMarvelStory(story);
            processedMarvelStories.add(processedMarvelStory);
        }
        return processedMarvelStories;
    }

}

