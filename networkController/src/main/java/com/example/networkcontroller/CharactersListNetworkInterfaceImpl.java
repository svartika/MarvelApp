package com.example.networkcontroller;

import com.example.controllers.ListAndCount;
import com.example.controllers.characterslist.CharactersListNetworkInterface;
import com.example.entitiy.models.MarvelCharacter;
import com.example.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;

public class CharactersListNetworkInterfaceImpl extends CharactersListNetworkInterface {
    Logger logger;

    @Inject
    MarvelRetrofitEndpointApi marvelRetrofitEndpointApi;

    @Inject
    public CharactersListNetworkInterfaceImpl(Logger logger) {
        this.logger = logger;
    }

    /*public Observable<List<Change>> loadChanges() {
        Observable<List<Change>> call = retrofitEndpointApi.loadChanges("status:open");
        return call;
    }*/

    /*public Observable<List<MarvelCharacter>> loadMarvelCharactersRaw() {
        logger.d("Indivar", "Loading marvel characters");
        Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters().map(
                marvelCharactersLoadResponse -> {
                    return marvelCharactersLoadResponse.data.characters;
                }
        );
        return call;
    }

    public Observable<List<MarvelCharacter>> searchCharacterRaw(String nameStartsWith, String offset, int limit) {
        logger.d("Vartika", "search marvel characters");
        Observable<List<MarvelCharacter>> call = marvelRetrofitEndpointApi.searchNameStartsWith(nameStartsWith)
                .map(
                        marvelCharactersLoadResponse -> {
                            return  marvelCharactersLoadResponse.data.characters;
                        }
                );
        return call;
    }*/

    public Observable<ListAndCount<MarvelCharacter>> loadMarvelCharactersRaw(int offset, int limit) {
        logger.d("Indivar", "Loading marvel characters");
        Observable<ListAndCount<MarvelCharacter>> call = marvelRetrofitEndpointApi.loadCharacters(offset, limit).map(
                marvelCharactersLoadResponse -> {
                    return new ListAndCount<MarvelCharacter>(marvelCharactersLoadResponse.data.characters, marvelCharactersLoadResponse.data.total);
                }
        );
        return call;
    }

    public Observable<ListAndCount<MarvelCharacter>> searchCharacterRaw(String nameStartsWith, int offset, int limit) {
        logger.d("Vartika", "search marvel characters");
        Observable<ListAndCount<MarvelCharacter>> call = marvelRetrofitEndpointApi.searchNameStartsWith(nameStartsWith, offset, limit)
                .map(
                        marvelCharactersLoadResponse -> {
                            return  new ListAndCount<MarvelCharacter>(marvelCharactersLoadResponse.data.characters, marvelCharactersLoadResponse.data.total);
                        }
                );
        return call;
    }
}

