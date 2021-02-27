package com.perpy.controllers.seriesdetail;

import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelStory;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class SeriesDetailNetworkInterface {
    public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersForSeriesRaw(int seriesId);

    public Observable<List<ProcessedMarvelCharacter>>loadMarvelCharactersForSeries(int seriesId) {
        return loadMarvelCharactersForSeriesRaw(seriesId).map(  charactersList -> {
                return processRawResponseCharacters(charactersList);
            });
    }

    List<ProcessedMarvelCharacter>processRawResponseCharacters(List<MarvelCharacter> charactersList) {
        List<ProcessedMarvelCharacter> processedCharacters = new ArrayList<>();
        for(MarvelCharacter character:charactersList) {
            ProcessedMarvelCharacter processedMarvelCharacter = new ProcessedMarvelCharacter(character);
            processedCharacters.add(processedMarvelCharacter);
        }
        return processedCharacters;
    }

    public abstract Observable<List<MarvelComic>> loadMarvelComicsForSeriesRaw(int seriesId);

    public Observable<List<ProcessedMarvelComic>> loadMarvelComicsForSeries(int seriesId) {
        return loadMarvelComicsForSeriesRaw(seriesId).map (comicsList -> {
           return processRawResponseComics(comicsList);
        });
    }

    List<ProcessedMarvelComic> processRawResponseComics(List<MarvelComic> comicsList) {
        List<ProcessedMarvelComic> processedComics = new ArrayList<>();
        for(MarvelComic comic: comicsList) {
            ProcessedMarvelComic processedMarvelComic  = new ProcessedMarvelComic(comic);
            processedComics.add(processedMarvelComic);
        }
        return processedComics;
    }

    public abstract Observable<List<MarvelStory>> loadMarvelStoriesForSeriesRaw(int seriesId);

    public Observable<List<ProcessedMarvelStory>> loadMarvelStoriesForSeries(int seriesId) {
        return loadMarvelStoriesForSeriesRaw(seriesId).map(storiesList -> {
            return processRawResponseStories(storiesList);
        }) ;
    }

    List<ProcessedMarvelStory> processRawResponseStories(List<MarvelStory> storiesList) {
        List<ProcessedMarvelStory> processedMarvelStories = new ArrayList<>();
        for(MarvelStory story: storiesList) {
            ProcessedMarvelStory processedMarvelStory = new ProcessedMarvelStory(story);
            processedMarvelStories.add(processedMarvelStory);
        }
        return processedMarvelStories;
    }

    public abstract Observable<List<MarvelEvent>> loadMarvelEventsForSeriesRaw(int seriesId);

    public Observable<List<ProcessedMarvelEvent>> loadMarvelForEvents(int seriesId) {
        return loadMarvelEventsForSeriesRaw(seriesId).map ( eventsList -> {
            return processRawResponseEvents(eventsList);
        });
    }

    List<ProcessedMarvelEvent> processRawResponseEvents(List<MarvelEvent> eventsList) {
        List<ProcessedMarvelEvent> processedMarvelEvents = new ArrayList<>();
        for(MarvelEvent event: eventsList) {
            ProcessedMarvelEvent processedMarvelEvent = new ProcessedMarvelEvent(event);
            processedMarvelEvents.add(processedMarvelEvent);
        }
        return processedMarvelEvents;
    }
}
