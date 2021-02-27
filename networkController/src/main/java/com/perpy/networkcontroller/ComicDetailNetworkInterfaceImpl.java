package com.perpy.networkcontroller;

import com.perpy.controllers.comicdetail.ComicDetailNetworkInterface;
import com.perpy.entitiy.models.MarvelCharacter;
import com.perpy.entitiy.models.MarvelEvent;
import com.perpy.entitiy.models.MarvelSeries;
import com.perpy.entitiy.models.MarvelStory;
import com.perpy.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ComicDetailNetworkInterfaceImpl extends ComicDetailNetworkInterface {
    @Inject
    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    ComicDetailNetworkInterfaceImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Observable<List<MarvelCharacter>> loadMarvelCharactersForComicRaw(int comicId) {
        Observable<List<MarvelCharacter>> o = marvelRetrofitEndpointApi.loadCharactersForComic(comicId).map(
          marvelCharactersLoadResponse -> {
              return marvelCharactersLoadResponse.data.characters;
          }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelSeries>> loadMarvelSeriesForComicRaw(int comicId) {
        Observable<List<MarvelSeries>> o = marvelRetrofitEndpointApi.loadSeriesForComic(comicId).map(
          marvelSeriesLoadResponse -> {
              return marvelSeriesLoadResponse.data.series;
          }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelStory>> loadMarvelStoriesForComicRaw(int comicId) {
        Observable<List<MarvelStory>> o = marvelRetrofitEndpointApi.loadStoriesForComic(comicId).map(
                marvelStoriesLoadResponse -> {
                    return marvelStoriesLoadResponse.data.stories;
                }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelEvent>> loadMarvelEventsForComicRaw(int comicId) {
        Observable<List<MarvelEvent>> o = marvelRetrofitEndpointApi.loadEventsForComic(comicId).map(
                marvelEventsLoadResponse -> {
                    return marvelEventsLoadResponse.data.events;
                }
        );
        return o;
    }
}
