package com.example.controllers.characterslist;


import com.example.controllers.ListAndCount;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.entitiy.models.MarvelCharacter;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class CharactersListNetworkInterface {
    //public abstract Observable<List<MarvelCharacter>> loadMarvelCharactersRaw();
    public abstract Observable<ListAndCount<MarvelCharacter>> loadMarvelCharactersRaw(int offset, int limit);

    public Observable<ListAndCount<ProcessedMarvelCharacter>> loadMarvelCharacters(int offset, int limit) {
        return loadMarvelCharactersRaw(offset, limit).map(response -> {
            return new ListAndCount<ProcessedMarvelCharacter>(processRawResponse(response.getList()), response.getTotal());
        });
    }

    //public abstract Observable<List<MarvelCharacter>> searchCharacterRaw(String nameStartsWith);
    public abstract Observable<ListAndCount<MarvelCharacter>> searchCharacterRaw(String nameStartsWith, int offset, int limit);

    public Observable<ListAndCount<ProcessedMarvelCharacter>> searchCharacter(String nameStartsWith, int offset, int limit) {
        if (nameStartsWith.isEmpty()) {
            return loadMarvelCharactersRaw(offset, limit).map(response -> {
                return new ListAndCount<ProcessedMarvelCharacter>(processRawResponse(response.getList()), response.getTotal());
            });
        } else {
            return searchCharacterRaw(nameStartsWith, offset, limit)
                    .map(response -> {
                         return new ListAndCount<ProcessedMarvelCharacter>(processRawResponse(response.getList()), response.getTotal());
                    });
        }
    }

    public Observable<ListAndCount<ProcessedMarvelCharacter>> searchCharactersFrom(String nameStartsWith, int offset, int limit) {
        if (nameStartsWith.isEmpty()) {
            return loadMarvelCharactersRaw(offset, limit).map(response -> {
                return new ListAndCount<ProcessedMarvelCharacter>(processRawResponse(response.getList()), response.getTotal());
            });
        } else {
            return searchCharacterRaw(nameStartsWith, offset, limit)
                    .map(response -> {
                        return new ListAndCount<ProcessedMarvelCharacter>(processRawResponse(response.getList()), response.getTotal());
                    });
        }
    }

    List<ProcessedMarvelCharacter> processRawResponse(List<MarvelCharacter> marvelCharactersList) {
        List<ProcessedMarvelCharacter> processedMarvelCharacters = new ArrayList<>();
        for (MarvelCharacter marvelCharacter : marvelCharactersList) {
            processedMarvelCharacters.add(new ProcessedMarvelCharacter(marvelCharacter));
        }
        return processedMarvelCharacters;
    }
    //Observable<List<Change>> loadChanges();
}
