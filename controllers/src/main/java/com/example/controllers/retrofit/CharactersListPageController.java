package com.example.controllers.retrofit;


import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.controllers.characterslist.MarvelCharacterClickListener;
import com.example.entitiy.models.logs.Logger;
import com.example.mviframework.ControlledLiveData;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.BehaviorSubject;
import io.reactivex.subjects.UnicastSubject;

public class CharactersListPageController implements AbsCharactersListPageController {
    MutableLiveData<State> stateLiveData = new MutableLiveData<State>();

    UnicastSubject<Effect> effectLiveData = UnicastSubject.create();
    ControlledLiveData<Effect> effectLiveDataControlled = new ControlledLiveData<>(effectLiveData);

    CharactersListNetworkInterface charactersListNetworkInterface;
    @Inject
    Logger logger;

    BehaviorSubject<String> keywordSubject = BehaviorSubject.createDefault("");
    MarvelCharacterClickListener marvelCharacterClickedListener = new MarvelCharacterClickedListenerImpl();
    State innerState = new State(false, false, null, marvelCharacterClickedListener, "");


    @Inject
    CharactersListPageController(CharactersListNetworkInterface charactersListNetworkInterface) {

        this.charactersListNetworkInterface = charactersListNetworkInterface;
        keywordSubject
                .distinctUntilChanged()
                .switchMap(keyword -> {
                    logger.d("Vartika", "search keyword: " + keyword);
                    return makeNetworkCallObservable(keyword);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())

                .subscribe(marvelCharactersList -> {
                            logger.d("Vartika", "marvelCharactersList: " + marvelCharactersList);
                            State clone = innerState.clone();
                            clone.loading = false;
                            clone.error = false;
                            clone.marvelCharactersList = marvelCharactersList;
                            innerState = clone;
                            stateLiveData.postValue(clone);
                        },
                        err -> {
                            logger.d("Vartika", err.getMessage());
                            State clone = innerState.clone();
                            clone.loading = false;
                            clone.error = true;
                            clone.marvelCharactersList = null;
                            innerState = clone;
                            stateLiveData.postValue(clone);
                        });

    }

    public void searchCharacter(String nameStartsWith) {
        logger.d("Vartika", "nameStartsWith: " + nameStartsWith);
       /* List<ProcessedMarvelCharacter> oldList = null;
        State previousState = charactersLiveData.getValue();
        if (previousState != null) {
            oldList = previousState.marvelCharactersList;
        }*/
        State clone = innerState.clone();
        clone.loading = true;
        clone.error = false;
        clone.searchStr = nameStartsWith;
        innerState = clone;
        stateLiveData.postValue(clone);
        keywordSubject.onNext(nameStartsWith);

    }

    private Observable<List<ProcessedMarvelCharacter>> makeNetworkCallObservable(String nameStartsWith) {
        if (nameStartsWith == null || nameStartsWith.isEmpty()) {
            return makeFullNetworkCallObservable();
        }
        return makeSearchNetworkCallObservable(nameStartsWith);
    }

    private Observable<List<ProcessedMarvelCharacter>> makeFullNetworkCallObservable() {
        if (charactersListNetworkInterface == null) {
            return Observable.just(new ArrayList<ProcessedMarvelCharacter>());
        }
        return charactersListNetworkInterface.loadMarvelCharacters().onErrorReturn(throwable -> {
            return new ArrayList<ProcessedMarvelCharacter>();
        });
    }

    private Observable<List<ProcessedMarvelCharacter>> makeSearchNetworkCallObservable(String nameStartsWith) {
        if (charactersListNetworkInterface == null) {
            return Observable.just(new ArrayList<ProcessedMarvelCharacter>());
        }
        return charactersListNetworkInterface.searchCharacter(nameStartsWith).onErrorReturn(throwable -> {
            return new ArrayList<ProcessedMarvelCharacter>();
        });
    }

    public LiveData<State> stateLiveData() {
        return stateLiveData;
    }

    public ControlledLiveData<Effect> effectLiveData() {
        return effectLiveDataControlled;
    }

    public class MarvelCharacterClickedListenerImpl implements MarvelCharacterClickListener<ProcessedMarvelCharacter> {

        @Override
        public void invoke(View view, ProcessedMarvelCharacter item) {
            logger.d("Vartika", "OnMarvelCharacterClicked: " + item);
            effectLiveData.onNext(new ClickEffect(view, item));
        }
    }

}
