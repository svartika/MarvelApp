package com.example.controllers.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entitiy.models.logs.Logger;

import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class CharactersListPageController implements AbsCharactersListPageController {
    MutableLiveData<State> charactersLiveData = new MutableLiveData<State>();
    @Inject
    CharactersListNetworkInterface charactersListNetworkInterface;
    @Inject
    Logger logger;

    @Inject
    CharactersListPageController() {
    }

    public void loadCharacters() {
        charactersLiveData.postValue(new State(true, false, null));
        Log.d("VartikaHilt", "retrofitController object hash -> " + charactersListNetworkInterface.hashCode());
        Observable<List<ProcessedMarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
        Disposable disposable = marvelCharacters
                .subscribeOn(Schedulers.io()) //create a background worker thread on which observable will carry out its task
                .observeOn(AndroidSchedulers.mainThread()) //get hold of the main thread so that results can be sent back to the UI
                .subscribe(marvelCharactersList -> {
                            Log.d("VartikaHilt", "Marvel Characters: " + marvelCharactersList.size());
                            charactersLiveData.postValue(new State(false, false, marvelCharactersList));
                            //displayMarvelCharacters(marvelCharactersList);
                        },
                        err -> {
                            Log.d("VartikaHilt", err.getLocalizedMessage());
                            charactersLiveData.postValue(new State(false, true, null));
                        });


    }

    public LiveData<State> getCharactersLiveData() {
        return charactersLiveData;
    }


}
