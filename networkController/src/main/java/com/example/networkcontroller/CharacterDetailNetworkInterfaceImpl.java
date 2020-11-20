package com.example.networkcontroller;

import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.entitiy.models.MarvelComic;
import com.example.entitiy.models.MarvelEvent;
import com.example.entitiy.models.MarvelSeries;
import com.example.entitiy.models.MarvelStory;
import com.example.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CharacterDetailNetworkInterfaceImpl extends CharacterDetailNetworkInterface {
    Logger logger;
    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    CharacterDetailNetworkInterfaceImpl(Logger logger) {
        this.logger = logger;
    }
    @Override
    public Observable<List<MarvelComic>> loadMarvelCharacterComicsRaw(int characterId){
        logger.d("VartikaHilt", "loading marvel character");
        Observable<List<MarvelComic>> call = marvelRetrofitEndpointApi.loadComics(characterId).map(
                marvelComicsResponse -> {
                    return marvelComicsResponse.data.comics;
                }
        );
        return call;
    }

    @Override
    public Observable<List<MarvelSeries>> loadMarvelCharacterSeriesRaw(int characterId){
        logger.d("VartikaHilt", "loading marvel series");
        Observable<List<MarvelSeries>> call = marvelRetrofitEndpointApi.loadSeries(characterId).map(
                marvelSeriesResponse -> {
                    return marvelSeriesResponse.data.series;
                }
        );
        return call;
    }

    @Override
    public Observable<List<MarvelStory>> loadMarvelCharacterStoriesRaw(int characterId){
        logger.d("VartikaHilt", "loading marvel stories");
        Observable<List<MarvelStory>> call = marvelRetrofitEndpointApi.loadStories(characterId).map(
                marvelStoriesResponse -> {
                    return marvelStoriesResponse.data.stories;
                }
        ).doOnError(throwable ->
                logger.d("Vartika", "stories error -> " + throwable.getMessage()));
        return call;
    }

    @Override
    public Observable<List<MarvelEvent>> loadMarvelCharacterEventsRaw(int characterId){
        logger.d("VartikaHilt", "loading marvel events");
        Observable<List<MarvelEvent>> call = marvelRetrofitEndpointApi.loadEvents(characterId).map(
                marvelEventsResponse -> {
                    return marvelEventsResponse.data.events;
                }
        );
        return call;
    }
}
