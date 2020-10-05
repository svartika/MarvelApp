package com.example.controllers.retrofit;


import com.example.entitiy.models.MarvelCharacter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class CharactersListNetworkInterface {
    public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersRaw();

    public Observable<List<ProcessedMarvelCharacter>> loadMarvelCharacters() {
        return loadMarvelCharactersRaw().map(marvelCharactersList -> {
                return processRawResponse(marvelCharactersList);
        });
    }

    public abstract Observable<List<MarvelCharacter>> searchCharacterRaw(String nameStartsWith);

    public Observable<List<ProcessedMarvelCharacter>> searchCharacter(String nameStartsWith) {
        return searchCharacterRaw(nameStartsWith)
                    .map(marvelCharactersList -> {
                        return processRawResponse(marvelCharactersList);
                    });
    }

    List<ProcessedMarvelCharacter> processRawResponse(List<MarvelCharacter> marvelCharactersList) {
        List <ProcessedMarvelCharacter> processedMarvelCharacters = new ArrayList<>();
        for ( MarvelCharacter marvelCharacter : marvelCharactersList) {
            processedMarvelCharacters.add(new ProcessedMarvelCharacter(marvelCharacter));
        }
        return processedMarvelCharacters;
    }
    //Observable<List<Change>> loadChanges();

}
