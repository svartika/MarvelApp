package com.example.controllers.characterslist;

import com.example.controllers.retrofit.ProcessedMarvelCharacter;

import java.util.List;

public class State {
    public List<ProcessedMarvelCharacter> charactersList;
    public String searchStr;
    public MarvelCharacterClickListener clickListener;
    public SearchTextChangedCallbackListener searchCallback;

    public State(List<ProcessedMarvelCharacter> charactersList, String searchStr, MarvelCharacterClickListener clickListener, SearchTextChangedCallbackListener searchCallback) {
        this.charactersList = charactersList;
        this.searchStr = searchStr;
        this.clickListener = clickListener;
        this.searchCallback = searchCallback;
    }

    public List<ProcessedMarvelCharacter> getCharactersList() {
        return charactersList;
    }

    public String getSearchStr() {
        return searchStr;
    }

    public MarvelCharacterClickListener getClickListener() {
        return clickListener;
    }

    public SearchTextChangedCallbackListener getSearchCallback() {
        return searchCallback;
    }
}
