package com.example.controllers.retrofit;

import com.example.entitiy.models.MarvelCharacter;

import io.reactivex.Observable;

public abstract class CharacterDetailNetworkInterface {

    public abstract Observable<MarvelCharacter> loadMarvelCharacter(String characterId);

    Observable<ProcessedMarvelCharacter> loadCharacterDetail(String characterId) {
        return loadMarvelCharacter(characterId).map(character -> {
            return new ProcessedMarvelCharacter(character);

        });
    }
}
