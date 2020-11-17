package com.example.controllers.characterdetail;

import android.util.Log;

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;
import com.example.mviframework.Runner;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;

public class CharacterDetailViewModelDelegate extends BaseMviDelegate<State, CharacterDetailViewModelDelegate.InnerState, Effect> {
    CharacterDetailNetworkInterface networkInterface;
    ProcessedMarvelCharacter character;
    public CharacterDetailViewModelDelegate(CharacterDetailNetworkInterface networkInterface, ProcessedMarvelCharacter character) {
        this.networkInterface = networkInterface;
        this.character = character;
        loadComics(character.id);
        /*enqueue(new Reducer<InnerState, Effect>() {
            @Override
            public Change<InnerState, Effect> reduce(InnerState innerState) {
                InnerState newInnerState = innerState.copy();
                newInnerState.character loadComics= character;
                newInnerState.error = false;
                newInnerState.loading = false;
                newInnerState.isImageLoaded = false;

                return asChange(newInnerState);
            }
        });*/
    }



    Runner callbackRunner = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState newInnerState = innerState.copy();
            newInnerState.loading = false;
            newInnerState.error = false;
            return withEffects(newInnerState, new Effect.ImageLoaded());
            //return asChange(newInnerState);
        }
    });

   /* ImageLoadedCallbackListener callbackListener = new ImageLoadedCallbackListener() {
        @Override
        public void callback() {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.isImageLoaded = true;
                    return asChange(newInnerState);
                }
            });
        }
    };*/

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(character, null,true, false));
    }

    @Override
    public State mapState(InnerState innerState) {
        Log.d("Vartika3", "comics in mapstate"+innerState.comics);
        return new State(innerState.character, innerState.comics, innerState.loading, innerState.error, callbackRunner);
    }

    static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelCharacter character;
        List<ProcessedMarvelComic> comics;
        InnerState(ProcessedMarvelCharacter character, List<ProcessedMarvelComic> comics, boolean loading, boolean error) {
            this.character = character;
            this.loading = loading;
            this.error = error;
            this.comics = comics;
        }
        InnerState copy() {
            return new InnerState(character, comics, loading, error);
        }
    }

    private void loadComics(int characterId) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelComic>> observable = networkInterface
                            .loadCharacterComics(characterId)
                            .onErrorReturn(throwable -> {
                                return new ArrayList<>();
                            });



        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(comics -> {
                    Log.d("Vartika3", "Reducer1: "+comics);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            Log.d("Vartika3", "Reducer2: "+comics);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.comics = comics;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

}
