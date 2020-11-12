package com.example.controllers.characterdetail;

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.entitiy.models.MarvelCharacter;

import io.reactivex.Observable;

public abstract class CharacterDetailNetworkInterface {

    public abstract Observable<MarvelCharacter> loadMarvelCharacter(int characterId);

    Observable<ProcessedMarvelCharacter> loadCharacterDetail(int characterId) {
        return loadMarvelCharacter(characterId).map(character -> {
            return new ProcessedMarvelCharacter(character);

        });
    }
}
