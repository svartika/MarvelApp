package com.example.controllers.comicdetail;

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

public class ComicDetailViewModelDelegate extends BaseMviDelegate<State, ComicDetailViewModelDelegate.InnerState, Effect> {
    ComicDetailNetworkInterface networkInterface;
    ProcessedMarvelComic comic;

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

    Runner callbackRunner = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState newInnerState = innerState.copy();
            newInnerState.loading = false;
            newInnerState.error = false;
            return withEffects(newInnerState, new Effect.ImageLoaded());
        }
    });

    public ComicDetailViewModelDelegate(ComicDetailNetworkInterface networkInterface, ProcessedMarvelComic comic) {
        this.networkInterface = networkInterface;
        this.comic = comic;
        loadCharacters(comic.id);
        loadSeries(comic.id);
        loadStories(comic.id);
        loadEvents(comic.id);

    }

    private void loadEvents(int id) {
        if(networkInterface==null) {
            return;
        }
        Observable<List<ProcessedMarvelEvent>> observable = networkInterface
                .loadEventsForComic(comic.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map( events -> {
                    return new Reducer<InnerState, Effect>() {
                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            InnerState newInnerState = innerState.copy();
                            newInnerState.events = events;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    private void loadStories(int id) {
        if(networkInterface==null) {
            return;
        }
        Observable<List<ProcessedMarvelStory>> observable = networkInterface
                .loadStoriesForComic(comic.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map( stories -> {
                    return new Reducer<InnerState, Effect>() {
                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            InnerState newInnerState = innerState.copy();
                            newInnerState.stories = stories;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    private void loadSeries(int id) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelSeries>> observable = networkInterface
                .loadSeriesForComic(comic.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(series -> {
                   // Log.d("Vartika3", "Reducer1: series "+series);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            //Log.d("Vartika3", "Reducer2: series "+series);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.series = series;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    private void loadCharacters(int id) {
        if (networkInterface == null) {
            return;
        }
        Observable<List<ProcessedMarvelCharacter>> observable = networkInterface
                .loadCharactersForComic(comic.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable
                .map(characters -> {
                    // Log.d("Vartika3", "Reducer1: series "+series);
                    return new Reducer<InnerState, Effect>() {

                        @Override
                        public Change<InnerState, Effect> reduce(InnerState innerState) {
                            //Log.d("Vartika3", "Reducer2: series "+series);
                            InnerState newInnerState = innerState.copy();
                            newInnerState.characters = characters;
                            return asChange(newInnerState);
                        }
                    };
                });
        enqueue(reducerObservable);
    }

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(comic, null, null, null, null, true, false));
    }

    @Override
    public State mapState(ComicDetailViewModelDelegate.InnerState innerState) {
        State state = new State(innerState.comic, innerState.characters, innerState.series, innerState.stories, innerState.events,  innerState.loading, innerState.error, callbackRunner, cardClickListener);
        Log.d("Vartika", "Comic Detail mapState: "+state.toString());
        return state;
    }
    static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelComic comic;
        List<ProcessedMarvelCharacter> characters;
        List<ProcessedMarvelSeries> series;
        List<ProcessedMarvelStory> stories;
        List<ProcessedMarvelEvent> events;

        InnerState(ProcessedMarvelComic comic, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error) {
            this.comic = comic;
            this.loading = loading;
            this.error = error;
            this.characters = characters;
            this.series = series;
            this.stories = stories;
            this.events = events;
        }
        InnerState copy() {
            return new InnerState(comic, characters, series, stories, events, loading, error);
        }
    }
}
