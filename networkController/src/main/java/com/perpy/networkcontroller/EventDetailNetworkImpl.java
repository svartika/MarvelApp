package com.perpy.networkcontroller;

import com.perpy.controllers.eventdetail.EventDetailNetworkInterface;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelSeries;
import com.perpy.entitiy.models.MarvelStory;
import com.perpy.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class EventDetailNetworkImpl extends EventDetailNetworkInterface {
    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    EventDetailNetworkImpl(Logger logger) {  this.logger = logger;  }

    @Override
    public Observable<List<MarvelCharacter>> loadMarvelCharactersForEventRaw(int eventId) {
        Observable<List<MarvelCharacter>> observable = marvelRetrofitEndpointApi.loadCharactersForEvent(eventId).map(
          marvelCharactersLoadResponse -> {
              return  marvelCharactersLoadResponse.data.characters;
          }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelComic>> loadMarvelComicsForEventRaw(int eventId) {
        Observable<List<MarvelComic>> observable = marvelRetrofitEndpointApi.loadComicsForEvent(eventId).map(
                marvelComicsLoadResponse -> {
                    return marvelComicsLoadResponse.data.comics;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelSeries>> loadMarvelSeriesForEventRaw(int eventId) {
        Observable<List<MarvelSeries>> observable = marvelRetrofitEndpointApi.loadSeriesForEvent(eventId).map(
          marvelSeriesLoadResponse -> {
              return marvelSeriesLoadResponse.data.series;
          }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelStory>> loadMarvelStoriesForEventRaw(int eventId) {
        Observable<List<MarvelStory>> observable = marvelRetrofitEndpointApi.loadStoriesForEvent(eventId).map(
                marvelSeriesLoadResponse -> {
                    return marvelSeriesLoadResponse.data.stories;
                }
        );
        return observable;
    }

}
