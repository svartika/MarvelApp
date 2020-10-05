package com.example.networkcontroller;

import com.example.controllers.retrofit.CharactersListNetworkInterface;
import com.example.entitiy.models.logs.Logger;
import com.example.entitiy.models.Change;
import com.example.entitiy.models.MarvelCharacter;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CharactersListNetworkInterfaceImpl extends CharactersListNetworkInterface {
    Logger logger;

    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;
    @Inject
    RetrofitEndpointApi retrofitEndpointApi;

    @Inject
    public CharactersListNetworkInterfaceImpl(Logger logger) {
        this.logger = logger;
    }

    /*public Observable<List<Change>> loadChanges() {
        Observable<List<Change>> call = retrofitEndpointApi.loadChanges("status:open");
        return call;
    }*/

    public Observable<List<MarvelCharacter>> loadMarvelCharactersRaw() {
        logger.d("Indivar", "Loading marvel characters");
        Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters().map(
                marvelCharactersLoadResponse -> {
                    return marvelCharactersLoadResponse.data.characters;
                }
        );
        return call;
    }

    public Observable<List<MarvelCharacter>> searchCharacterRaw(String nameStartsWith) {
        logger.d("Vartika", "search marvel characters");
        Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.searchNameStartsWith(nameStartsWith)
                .map(
                        marvelCharactersLoadResponse -> {
                            return  marvelCharactersLoadResponse.data.characters;
                        }
                );
        return call;
    }


}

