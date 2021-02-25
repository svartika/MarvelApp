package com.example.controllers.characterdetail;

import android.util.Log;
import android.view.View;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
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

    CardClickListener cardClickListener = new CardClickListener<ProcessedMarvelItemBase>() {

        @Override
        public void invoke(View view, ProcessedMarvelItemBase item) {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    return withEffects(innerState, new Effect.ClickCardEffect<ProcessedMarvelItemBase>(view, item));
                }
            });
        }
    };

    public CharacterDetailViewModelDelegate(CharacterDetailNetworkInterface networkInterface, ProcessedMarvelCharacter character) {
        this.networkInterface = networkInterface;
        this.character = character;
        loadComics(character.id);
        loadSeries(character.id);
        loadStories(character.id);
        loadEvents(character.id);

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
        return asChange(new InnerState(character, null, null, null, null, true, false));
    }

    @Override
    public State mapState(InnerState innerState) {
        State state = new State(innerState.character, innerState.comics, innerState.series, innerState.stories, innerState.events,  innerState.loading, innerState.error, callbackRunner, cardClickListener);
        Log.d("Vartika", "Character Detail mapState: "+state.toString());
        return state;
    }

    static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelCharacter character;
        List<ProcessedMarvelComic> comics;
        List<ProcessedMarvelSeries> series;
        List<ProcessedMarvelStory> stories;
        List<ProcessedMarvelEvent> events;
        InnerState(ProcessedMarvelCharacter character, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error) {
            this.character = character;
            this.loading = loading;
            this.error = error;
            this.comics = comics;
            this.series = series;
            this.stories = stories;
            this.events = events;
        }
        InnerState copy() {
            return new InnerState(character, comics, series, stories, events, loading, error);
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

    private void loadSeries(int characterId) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelSeries>> observable = networkInterface
                .loadCharacterSeries(characterId)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });



        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(series -> {
                    Log.d("Vartika3", "Reducer1: series "+series);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            Log.d("Vartika3", "Reducer2: series "+series);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.series = series;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    private void loadStories(int characterId) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelStory>> observable = networkInterface
                .loadCharacterStories(characterId)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });



        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(stories -> {
                    Log.d("Vartika3", "Reducer1: stories -> "+stories);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            Log.d("Vartika3", "Reducer2: stories -> "+stories);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.stories = stories;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    private void loadEvents(int characterId) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelEvent>> observable = networkInterface
                .loadCharacterEvents(characterId)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });



        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(events -> {
                    Log.d("Vartika3", "Reducer1: events -> "+events);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            Log.d("Vartika3", "Reducer2: events -> "+events);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.events = events;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }
}
