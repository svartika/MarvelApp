package com.perpy.controllers.storydetail;

import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelSeries;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class StoryDetailNetworkInterface {
    public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersForStoryRaw(int storyId);

    public Observable<List<ProcessedMarvelCharacter>> loadMarvelCharactersForStory(int storyId) {
        return loadMarvelCharactersForStoryRaw(storyId).map( charactersList -> {
            return processRawCharactersResponse(charactersList);
        });
    }

    List<ProcessedMarvelCharacter> processRawCharactersResponse(List<MarvelCharacter> charactersList) {
        List<ProcessedMarvelCharacter> processedMarvelCharacters = new ArrayList<>();
        for(MarvelCharacter marvelCharacter: charactersList) {
            ProcessedMarvelCharacter processedMarvelCharacter = new ProcessedMarvelCharacter(marvelCharacter);
            processedMarvelCharacters.add(processedMarvelCharacter);
        }
        return  processedMarvelCharacters;
    }

    public abstract Observable<List<MarvelComic>> loadMarvelComicsForStoryRaw(int storyId);

    public Observable<List<ProcessedMarvelComic>> loadMarvelComicsForStory(int storyId) {
        return loadMarvelComicsForStoryRaw(storyId).map( comicsList -> {
            return processRawComicsResponse(comicsList);
        });
    }

    List<ProcessedMarvelComic> processRawComicsResponse(List<MarvelComic> comicsList) {
        List<ProcessedMarvelComic> processedMarvelComics = new ArrayList<>();
        for(MarvelComic comic: comicsList) {
            ProcessedMarvelComic processedMarvelComic = new ProcessedMarvelComic(comic);
            processedMarvelComics.add(processedMarvelComic);
        }
        return processedMarvelComics;
    }

    public abstract Observable<List<MarvelSeries>> loadMarvelSeriesForStoryRaw(int storyId);

    public Observable<List<ProcessedMarvelSeries>> loadMarvelSereisForStory(int storyId) {
        return loadMarvelSeriesForStoryRaw(storyId).map( seriesList -> {
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

    public abstract Observable<List<MarvelEvent>> loadMarvelEventsForStoryRaw(int storyId);

    public Observable<List<ProcessedMarvelEvent>> loadMarvelEventsForStory(int storyId) {
        return loadMarvelEventsForStoryRaw(storyId).map( eventsList -> {
            return processsRawEventsResponse(eventsList);
        });
    }

    List<ProcessedMarvelEvent> processsRawEventsResponse(List<MarvelEvent> eventsList) {
        List<ProcessedMarvelEvent> processedMarvelEvents = new ArrayList<>();
        for(MarvelEvent event: eventsList) {
            ProcessedMarvelEvent processedMarvelEvent = new ProcessedMarvelEvent(event);
            processedMarvelEvents.add(processedMarvelEvent);
        }
        return processedMarvelEvents;
    }
}
