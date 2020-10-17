package com.example.controllers.retrofit;

import android.view.View;

import androidx.lifecycle.LiveData;

import java.util.List;

public interface AbsCharactersListPageController {
    LiveData<State> stateLiveData();

    ControlledLiveData<Effect> effectLiveData();

    void loadCharacters();

    void searchCharacter(String nameStartsWith);

    class State {
        public boolean loading;
        public boolean error;
        public List<ProcessedMarvelCharacter> marvelCharactersList;
        public AbsMarvelCharacterClickedListener marvelCharacterClickedListener;

        public State(boolean loading, boolean error, List<ProcessedMarvelCharacter> marvelCharactersList, AbsMarvelCharacterClickedListener marvelCharacterClickedListener) {
            this.loading = loading;
            this.error = error;
            this.marvelCharactersList = marvelCharactersList;
            this.marvelCharacterClickedListener = marvelCharacterClickedListener;
        }

    }

    abstract class AbsMarvelCharacterClickedListener<T> {
        public abstract void invoke(View view, T item);
    }

    public class ClickEffect<T> extends Effect {
        public T item;
        public View view;

        public ClickEffect(View view, T item) {
            this.item = item;
            this.view = view;
        }
    }
}
