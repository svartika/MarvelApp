package com.example.controllers.retrofit;



import com.example.entitiy.models.Change;
import com.example.entitiy.models.MarvelCharacter;

import java.util.List;

import io.reactivex.Observable;

public interface CharactersListController {
    Observable<List<MarvelCharacter>> loadMarvelCharacters();
    Observable<List<Change>> loadChanges();

    Observable<MarvelCharacter> loadMarvelCharacter(String characterId);
}
