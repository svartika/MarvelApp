package com.example.controllers.characterslist;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;

import java.util.List;
import java.util.Objects;

public class State {
    public List<ProcessedMarvelCharacter> charactersList;
    public String searchStr;
    public CardClickListener clickListener;
    public SearchTextChangedCallbackListener searchCallback;

    public State(List<ProcessedMarvelCharacter> charactersList, String searchStr, CardClickListener clickListener, SearchTextChangedCallbackListener searchCallback) {
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

    public CardClickListener getClickListener() {
        return clickListener;
    }

    public SearchTextChangedCallbackListener getSearchCallback() {
        return searchCallback;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return getCharactersList().equals(state.getCharactersList()) &&
                getSearchStr().equals(state.getSearchStr()) &&
                getClickListener().equals(state.getClickListener()) &&
                getSearchCallback().equals(state.getSearchCallback());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCharactersList(), getSearchStr(), getClickListener(), getSearchCallback());
    }
}
