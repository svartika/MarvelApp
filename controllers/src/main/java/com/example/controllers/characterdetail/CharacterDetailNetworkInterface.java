package com.example.controllers.characterdetail;

import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.entitiy.models.MarvelComic;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public abstract class CharacterDetailNetworkInterface {

    public abstract Observable<List<MarvelComic>> loadMarvelCharacterComicsRaw(int characterId);

    Observable<List<ProcessedMarvelComic>> loadCharacterComics(int characterId) {
        return loadMarvelCharacterComicsRaw(characterId).map(comicsList -> {
            return processRawResponse(comicsList);
        });
    }

    private List<ProcessedMarvelComic> processRawResponse(List<MarvelComic> comicsList) {
        List<ProcessedMarvelComic> processedMarvelComics = new ArrayList<>();
        for (MarvelComic marvelComic : comicsList) {
            processedMarvelComics.add(new ProcessedMarvelComic(marvelComic));
        }
        return processedMarvelComics;
    }

}
