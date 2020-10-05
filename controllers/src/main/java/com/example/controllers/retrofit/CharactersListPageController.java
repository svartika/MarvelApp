package com.example.controllers.retrofit;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entitiy.models.logs.Logger;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;

public class CharactersListPageController implements AbsCharactersListPageController {
    MutableLiveData<State> charactersLiveData = new MutableLiveData<State>();

    CharactersListNetworkInterface charactersListNetworkInterface;
    @Inject
    Logger logger;

    BehaviorSubject<String> keywordSubject = BehaviorSubject.createDefault("");

    @Inject
    CharactersListPageController(CharactersListNetworkInterface charactersListNetworkInterface) {
        this.charactersListNetworkInterface = charactersListNetworkInterface;
        keywordSubject.switchMap(keyword -> {
            return makeNetworkCallObservable(keyword);
        }).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(marvelCharactersList -> {
                    logger.d("Vartika", "marvelCharactersList: "+marvelCharactersList);
                    charactersLiveData.postValue(new State(false, false, marvelCharactersList));
                },
                err -> {
                    logger.d("Vartika", err.getMessage());
                    charactersLiveData.postValue(new State(false, true, null));
                });
    }

    public void loadCharacters() {
        charactersLiveData.postValue(new State(true, false, null));
        Log.d("VartikaHilt", "retrofitController object hash -> " + charactersListNetworkInterface.hashCode());
        Observable<List<ProcessedMarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
        //ToDo:   dispose off
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

    public void searchCharacter(String nameStartsWith) {
        logger.d("Vartika", "nameStartsWith: "+nameStartsWith);
        charactersLiveData.postValue(new State(true, false, null));
        keywordSubject.onNext(nameStartsWith);

    }

    private Observable<List<ProcessedMarvelCharacter>> makeNetworkCallObservable(String nameStartsWith) {
        if(nameStartsWith==null || nameStartsWith.isEmpty()) {
            return makeFullNetworkCallObservable();
        }
        return makeSearchNetworkCallObservable(nameStartsWith);
    }
    private Observable<List<ProcessedMarvelCharacter>> makeFullNetworkCallObservable() {
        if(charactersListNetworkInterface==null) {
            return Observable.just(new ArrayList<ProcessedMarvelCharacter>());
        }
        return charactersListNetworkInterface.loadMarvelCharacters().onErrorReturn(throwable -> {return new ArrayList<ProcessedMarvelCharacter>();});
    }

    private Observable<List<ProcessedMarvelCharacter>> makeSearchNetworkCallObservable(String nameStartsWith) {
        if(charactersListNetworkInterface==null) {
            return Observable.just(new ArrayList<ProcessedMarvelCharacter>());
        }
        return charactersListNetworkInterface.searchCharacter(nameStartsWith).onErrorReturn(throwable -> {return new ArrayList<ProcessedMarvelCharacter>();});
        /*Disposable disposable = marvelCharacters
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(marvelCharactersList -> {
                            logger.d("Vartika", "Marvel Characters with name: " + nameStartsWith + " are: " + marvelCharactersList.size());
                            charactersLiveData.postValue(new State(false, false, marvelCharactersList));
                        },
                        err -> {
                            logger.d("Vartika", err.getMessage());
                            charactersLiveData.postValue(new State(false, true, null));
                        });*/
    }

    public LiveData<State> stateLiveData() {
        return charactersLiveData;
    }


}
