package com.perpy.networkcontroller;

import com.perpy.controllers.seriesdetail.SeriesDetailNetworkInterface;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelComic;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelStory;
import com.perpy.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class SeriesDetailNetworkInterfaceImpl extends SeriesDetailNetworkInterface {
    @Inject
    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    SeriesDetailNetworkInterfaceImpl(Logger logger) {  this.logger = logger;  }

    @Override
    public Observable<List<MarvelCharacter>> loadMarvelCharactersForSeriesRaw(int seriesId) {
        Observable<List<MarvelCharacter>> observable = marvelRetrofitEndpointApi.loadCharactersForSeries(seriesId).map(
                marvelCharactersLoadResponse -> {
                    return marvelCharactersLoadResponse.data.characters;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelComic>> loadMarvelComicsForSeriesRaw(int seriesId) {
        Observable<List<MarvelComic>> observable = marvelRetrofitEndpointApi.loadComicsForSeries(seriesId).map(
                marvelComicsLoadResponse -> {
                    return marvelComicsLoadResponse.data.comics;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelStory>> loadMarvelStoriesForSeriesRaw(int seriesId) {
        Observable<List<MarvelStory>> observable = marvelRetrofitEndpointApi.loadStoriesForSeries(seriesId).map(
                marvelStoriesLoadResponse -> {
                    return marvelStoriesLoadResponse.data.stories;
                }
        );
        return observable;
    }

    @Override
    public Observable<List<MarvelEvent>> loadMarvelEventsForSeriesRaw(int seriesId) {
        Observable<List<MarvelEvent>> observable = marvelRetrofitEndpointApi.loadEventsForSeries(seriesId).map(
                marvelEventsLoadResponse -> {
                    return marvelEventsLoadResponse.data.events;
                }
        );
        return observable;
    }
}
