package com.example.controllers.retrofit;

import android.view.View;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.entitiy.models.logs.Logger;

import javax.inject.Inject;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.UnicastSubject;

public class CharacterDetailPageController {
    MutableLiveData<State> marvelCharacterLiveData = new MutableLiveData<>();
    UnicastSubject<Effect> effectLiveData = UnicastSubject.create();
    ControlledLiveData<Effect> effectLiveDataControlled = new ControlledLiveData<>(effectLiveData);
    ICallbackListerner imageLoadCallback = new ICallbackListerner() {
        @Override
        public void callback() {
            logger.d("Vartika", "CallbackListener: onCallbackListener ");
            effectLiveData.onNext(new ImageLoaded());
        }
    };

    @Inject
    Logger logger;
    @Inject
    CharacterDetailNetworkInterface characterDetailNetworkInterface;

    @Inject
    CharacterDetailPageController() {
    }

    public void loadCharacterDetails(int characterID) {
        marvelCharacterLiveData.postValue(new State(true, false, null, imageLoadCallback));
        Observable<ProcessedMarvelCharacter> marvelCharacterObservable = characterDetailNetworkInterface.loadCharacterDetail(characterID);
        marvelCharacterObservable
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        processedMarvelCharacter -> {
                            marvelCharacterLiveData.postValue(new State(false, false, processedMarvelCharacter, imageLoadCallback));
                        },
                        err -> {
                            marvelCharacterLiveData.postValue(new State(false, true, null, imageLoadCallback));
                            logger.d("VartikaHilt", err.getLocalizedMessage());
                        },
                        () -> {

                        });
    }

    public LiveData<State> getStateLiveData() {
        return marvelCharacterLiveData;
    }

    public static class State {
        public boolean loading;
        public boolean error;
        public ProcessedMarvelCharacter character;
        public ICallbackListerner callbackListener;

        public State(boolean loading, boolean error, ProcessedMarvelCharacter character, ICallbackListerner callbackListener) {
            this.loading = loading;
            this.error = error;
            this.character = character;
            this.callbackListener = callbackListener;
        }
    }

    public ControlledLiveData<Effect> effectLiveData() {
        return effectLiveDataControlled;
    }

    public class ImageLoaded extends Effect {
        public View view;
        public ImageLoaded( ) {
        }
    }

}
