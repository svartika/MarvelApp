package com.example.networkcontroller;

import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.entitiy.models.MarvelComic;
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
}
