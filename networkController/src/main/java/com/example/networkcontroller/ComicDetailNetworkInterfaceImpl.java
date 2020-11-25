package com.example.networkcontroller;

import com.example.controllers.comicdetail.ComicDetailNetworkInterface;
import com.example.entitiy.models.MarvelCharacter;
import com.example.entitiy.models.MarvelEvent;
import com.example.entitiy.models.MarvelSeries;
import com.example.entitiy.models.MarvelStory;
import com.example.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class ComicDetailNetworkInterfaceImpl extends ComicDetailNetworkInterface {

    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    ComicDetailNetworkInterfaceImpl(Logger logger) {
        this.logger = logger;
    }

    @Override
    public Observable<List<MarvelCharacter>> loadMarvelCharactersForComicRaw(int comicId) {
        Observable<List<MarvelCharacter>> o = marvelRetrofitEndpointApi.loadCharactersForComics(comicId).map(
          marvelCharactersLoadResponse -> {
              return marvelCharactersLoadResponse.data.characters;
          }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelSeries>> loadMarvelSeriesForComicRaw(int comicId) {
        Observable<List<MarvelSeries>> o = marvelRetrofitEndpointApi.loadSeriesForComics(comicId).map(
          marvelSeriesLoadResponse -> {
              return marvelSeriesLoadResponse.data.series;
          }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelStory>> loadMarvelStoriesForComicRaw(int comicId) {
        Observable<List<MarvelStory>> o = marvelRetrofitEndpointApi.loadStoriesForComics(comicId).map(
                marvelStoriesLoadResponse -> {
                    return marvelStoriesLoadResponse.data.stories;
                }
        );
        return o;
    }

    @Override
    public Observable<List<MarvelEvent>> loadMarvelEventsForComicRaw(int comicId) {
        Observable<List<MarvelEvent>> o = marvelRetrofitEndpointApi.loadEventsForComics(comicId).map(
                marvelEventsLoadResponse -> {
                    return marvelEventsLoadResponse.data.events;
                }
        );
        return o;
    }
}
