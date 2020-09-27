package com.example.controllers.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entitiy.models.MarvelCharacter;
import com.example.entitiy.models.logs.Logger;

import java.util.concurrent.TimeUnit;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class CharacterDetailPageController {
    MutableLiveData<State> marvelCharacterLiveData = new MutableLiveData<>();

    @Inject
    Logger logger;
    @Inject
    CharacterDetailNetworkInterface characterDetailNetworkInterface;
    @Inject
    CharacterDetailPageController() { }

    public void loadCharacterDetails(String characterID) {
        marvelCharacterLiveData.postValue(new State(true, false, null));
        Observable<ProcessedMarvelCharacter> marvelCharacterObservable = characterDetailNetworkInterface.loadCharacterDetail(characterID);
        marvelCharacterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                processedMarvelCharacter -> {
                    marvelCharacterLiveData.postValue(new State(false, false, processedMarvelCharacter));
                },
                err -> {
                    marvelCharacterLiveData.postValue(new State(false, true, null));
                    logger.d("VartikaHilt", err.getLocalizedMessage());
                },
                () -> {

                });
    }
    public LiveData<State> getCharacterDetailLiveData() {
        return marvelCharacterLiveData;
    }
    public static class State {
        public boolean loading;
        public boolean error;
        public ProcessedMarvelCharacter character;
        public State(boolean loading, boolean error, ProcessedMarvelCharacter character) {
            this.loading = loading;
            this.error = error;
            this.character = character;
        }
    }
}
