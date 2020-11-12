package com.example.networkcontroller;

import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.entitiy.models.MarvelCharacter;
import com.example.entitiy.models.logs.Logger;

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
    public Observable<MarvelCharacter> loadMarvelCharacter(int characterId){
        logger.d("VartikaHilt", "loading marvel character");
        Observable<MarvelCharacter> call = marvelRetrofitEndpointApi.loadCharacter(characterId).map(
                marvelCharacterResponse -> {
                    return marvelCharacterResponse.data.characters.get(0);
                }
        );
        return call;
    }
}
