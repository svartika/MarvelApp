package com.example.controllers.retrofit;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface AbsCharactersListPageController {
    LiveData<State> stateLiveData();

    void loadCharacters();
    void searchCharacter(String nameStartsWith);

    class State {
        public boolean loading;
        public boolean error;
        public List<ProcessedMarvelCharacter> marvelCharactersList;

        public State(boolean loading, boolean error, List<ProcessedMarvelCharacter> marvelCharactersList) {
            this.loading = loading;
            this.error = error;
            this.marvelCharactersList = marvelCharactersList;
        }
    }
}
