package com.example.networkcontroller;

import com.example.controllers.storydetail.StoryDetailNetworkInterface;
import com.example.entitiy.models.MarvelCharacter;
import com.example.entitiy.models.MarvelComic;
import com.example.entitiy.models.MarvelEvent;
import com.example.entitiy.models.MarvelSeries;
import com.example.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class StoryDetailNetworkImpl extends StoryDetailNetworkInterface {
    @Inject
    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    StoryDetailNetworkImpl(Logger logger) {  this.logger = logger;  }

    @Override
    public Observable<List<MarvelCharacter>> loadMarvelCharactersForStoryRaw(int storyId) {
        Observable<List<MarvelCharacter>> observable = marvelRetrofitEndpointApi.loadCharactersForStory(storyId).map(
                marvelCharactersLoadResponse -> {
                    return marvelCharactersLoadResponse.data.characters;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelComic>> loadMarvelComicsForStoryRaw(int storyId) {
        Observable<List<MarvelComic>> observable = marvelRetrofitEndpointApi.loadComicsForStory(storyId).map(
                marvelComicsLoadResponse -> {
                    return marvelComicsLoadResponse.data.comics;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelSeries>> loadMarvelSeriesForStoryRaw(int storyId) {
        Observable<List<MarvelSeries>> observable = marvelRetrofitEndpointApi.loadSeriesForStory(storyId).map(
          marvelSeriesLoadResponse -> {
              return marvelSeriesLoadResponse.data.series;
          }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelEvent>> loadMarvelEventsForStoryRaw(int storyId) {
        Observable<List<MarvelEvent>> observable = marvelRetrofitEndpointApi.loadEventsForStory(storyId).map(
          marvelEventsLoadResponse -> {
              return marvelEventsLoadResponse.data.events;
          }
        );
        return observable;
    }
}
