package com.perpy.controllers.comicdetail;

import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelSeries;
import com.perpy.entitiy.models.MarvelStory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class ComicDetailNetworkInterface {
    public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersForComicRaw(int comicId);

    Observable<List<ProcessedMarvelCharacter>> loadCharactersForComic(int comicId) {
        return loadMarvelCharactersForComicRaw(comicId).map(charactersList -> {
            return processRawResponseCharacters(charactersList);
        });
    }

    private List<ProcessedMarvelCharacter> processRawResponseCharacters(List<MarvelCharacter> charactersList) {
        List<ProcessedMarvelCharacter> processedMarvelCharacters = new ArrayList<>();
        for (MarvelCharacter marvelCharacter : charactersList) {
            processedMarvelCharacters.add(new ProcessedMarvelCharacter(marvelCharacter));
        }
        return processedMarvelCharacters;
    }


    public abstract Observable<List<MarvelSeries>> loadMarvelSeriesForComicRaw(int comicId);

    Observable<List<ProcessedMarvelSeries>> loadSeriesForComic(int comicId) {
        return loadMarvelSeriesForComicRaw(comicId).map(seriesList -> {
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


    public abstract Observable<List<MarvelStory>> loadMarvelStoriesForComicRaw(int comicId);

    Observable<List<ProcessedMarvelStory>> loadStoriesForComic(int comicId) {
        return loadMarvelStoriesForComicRaw(comicId).map(storiesList -> {
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


    public abstract Observable<List<MarvelEvent>> loadMarvelEventsForComicRaw(int characterId);

    Observable<List<ProcessedMarvelEvent>> loadEventsForComic(int comicId) {
        return loadMarvelEventsForComicRaw(comicId).map(eventsList -> {
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
