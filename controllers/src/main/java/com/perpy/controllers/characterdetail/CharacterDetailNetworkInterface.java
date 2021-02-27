package com.perpy.controllers.characterdetail;

import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelSeries;
import com.perpy.entitiy.models.MarvelStory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class CharacterDetailNetworkInterface {

    public abstract Observable<List<MarvelComic>> loadMarvelCharacterComicsRaw(int characterId);

    Observable<List<ProcessedMarvelComic>> loadCharacterComics(int characterId) {
        return loadMarvelCharacterComicsRaw(characterId).map(comicsList -> {
            return processRawResponseComics(comicsList);
        });
    }

    private List<ProcessedMarvelComic> processRawResponseComics(List<MarvelComic> comicsList) {
        List<ProcessedMarvelComic> processedMarvelComics = new ArrayList<>();
        for (MarvelComic marvelComic : comicsList) {
            processedMarvelComics.add(new ProcessedMarvelComic(marvelComic));
        }
        return processedMarvelComics;
    }


    public abstract Observable<List<MarvelSeries>> loadMarvelCharacterSeriesRaw(int characterId);

    Observable<List<ProcessedMarvelSeries>> loadCharacterSeries(int characterId) {
        return loadMarvelCharacterSeriesRaw(characterId).map(seriesList -> {
            return processRawResponseSeries(seriesList);
        });
    }

    private List<ProcessedMarvelSeries> processRawResponseSeries(List<MarvelSeries> seriesList) {
        List<ProcessedMarvelSeries> processedMarvelSeries = new ArrayList<>();
        for (MarvelSeries series : seriesList) {
            processedMarvelSeries.add(new ProcessedMarvelSeries(series));
        }
        return processedMarvelSeries;
    }


    public abstract Observable<List<MarvelStory>> loadMarvelCharacterStoriesRaw(int characterId);

    Observable<List<ProcessedMarvelStory>> loadCharacterStories(int characterId) {
        return loadMarvelCharacterStoriesRaw(characterId).map(storiesList -> {
            return processRawResponseStories(storiesList);
        });
    }

    private List<ProcessedMarvelStory> processRawResponseStories(List<MarvelStory> storiesList) {
        List<ProcessedMarvelStory> processedMarvelStories = new ArrayList<>();
        for (MarvelStory story : storiesList) {
            processedMarvelStories.add(new ProcessedMarvelStory(story));
        }
        return processedMarvelStories;
    }


    public abstract Observable<List<MarvelEvent>> loadMarvelCharacterEventsRaw(int characterId);

    Observable<List<ProcessedMarvelEvent>> loadCharacterEvents(int characterId) {
        return loadMarvelCharacterEventsRaw(characterId).map(eventsList -> {
            return processRawResponseEvents(eventsList);
        });
    }

    private List<ProcessedMarvelEvent> processRawResponseEvents(List<MarvelEvent> eventsList) {
        List<ProcessedMarvelEvent> processedMarvelEvents = new ArrayList<>();
        for (MarvelEvent marvelEvent : eventsList) {
            processedMarvelEvents.add(new ProcessedMarvelEvent(marvelEvent));
        }
        return processedMarvelEvents;
    }
}
