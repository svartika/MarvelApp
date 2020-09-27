package com.example.networkcontroller;

import com.example.controllers.retrofit.CharactersListController;
import com.example.entitiy.models.logs.Logger;
import com.example.entitiy.models.Change;
import com.example.entitiy.models.MarvelCharacter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CharactersListControllerImpl implements CharactersListController {
    Logger logger;

    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;
    @Inject
    RetrofitEndpointApi retrofitEndpointApi;

    @Inject
    public CharactersListControllerImpl(Logger logger) {
        this.logger = logger;
    }

    public Observable<List<Change>> loadChanges() {
        Observable<List<Change>> call = retrofitEndpointApi.loadChanges("status:open");
        return call;
    }

    public Observable<List<MarvelCharacter>> loadMarvelCharacters() {
        logger.d("Indivar", "Loading marvel characters");
        Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters().map(
                marvelCharactersLoadResponse -> {
                    return marvelCharactersLoadResponse.data.characters;
                }
        );
        return call;
    }

    public Observable<MarvelCharacter> loadMarvelCharacter(String characterId) {
        logger.d("VartikaHilt", "loading marvel character");
        Observable<MarvelCharacter> call = marvelRetrofitEndpointApi.loadCharacter(characterId).map(
                marvelCharacterResponse -> {
                    return marvelCharacterResponse.data.characters.get(0);
                }
        );
        return call;
    }
}

