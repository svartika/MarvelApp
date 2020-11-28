package com.example.controllers.seriesdetail;

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

public class SeriesDetailViewModelDelegate extends BaseMviDelegate<State, SeriesDetailViewModelDelegate.InnerState, Effect> {
    SeriesDetailNetworkInterface networkInterface;
    ProcessedMarvelSeries series;
    Runner callbackRunner = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState newInnerState = innerState.copy();
            newInnerState.loading = false;
            newInnerState.error = false;
            return withEffects(newInnerState, new Effect.ImageLoaded<>());
        }
    });
    CardClickListener clickListener = new CardClickListener<ProcessedMarvelItemBase>() {
        @Override
        public void invoke(View view, ProcessedMarvelItemBase item) {
            enqueue(new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    return withEffects(innerState, new Effect.CardClickedEffect<ProcessedMarvelItemBase>(view, item));
                }
            });
        }
    };

    public SeriesDetailViewModelDelegate(SeriesDetailNetworkInterface networkInterface, ProcessedMarvelSeries series) {
        this.networkInterface = networkInterface;
        this.series = series;
        loadCharacters();
        loadComics();
        loadStories();
        loadEvents();
    }

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(series, null, null, null, null, true, false));
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(innerState.series, innerState.characters, innerState.comics, innerState.stories, innerState.events, innerState.loading, innerState.error, callbackRunner, clickListener);
    }

    public static class InnerState{
        boolean loading;
        boolean error;
        ProcessedMarvelSeries series;
        List<ProcessedMarvelCharacter> characters;
        List<ProcessedMarvelComic> comics;
        List<ProcessedMarvelStory> stories;
        List<ProcessedMarvelEvent> events;

        public InnerState(ProcessedMarvelSeries series, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error) {
            this.loading = loading;
            this.error = error;
            this.series = series;
            this.characters = characters;
            this.comics = comics;
            this.stories = stories;
            this.events = events;
        }

        public InnerState copy() {
            return new InnerState(series, characters, comics, stories, events, loading, error);
        }
    }

    void loadCharacters() {
        if(networkInterface==null) { return; }
        Observable<List<ProcessedMarvelCharacter>> observable = networkInterface
                .loadMarvelCharactersForSeries(series.id)
                .onErrorReturn( throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map( characters -> {
            return new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.characters = characters;
                    return asChange(newInnerState);
                }
            };
        });
        enqueue(reducerObservable);
    }
    void loadComics(){
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelComic>> observable = networkInterface
                .loadMarvelComicsForSeries(series.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(comics -> {
           return new Reducer<InnerState, Effect>() {
               @Override
               public Change<InnerState, Effect> reduce(InnerState innerState) {
                   InnerState newInnerState = innerState.copy();
                   newInnerState.comics = comics;
                   return asChange(newInnerState);
               }
           };
        });
        enqueue(reducerObservable);
    }
    void loadStories(){
        if(networkInterface==null)return;
        Observable<List<ProcessedMarvelStory>> observable = networkInterface
                .loadMarvelStoriesForSeries(series.id)
                .onErrorReturn( throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(stories-> {
           return new Reducer<InnerState, Effect>() {
               @Override
               public Change<InnerState, Effect> reduce(InnerState innerState) {
                   InnerState newInnerState = innerState.copy();
                   newInnerState.stories = stories;
                   return asChange(newInnerState);
               }
           } ;
        });
        enqueue(reducerObservable);
    }
    void loadEvents(){
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelEvent>> observable = networkInterface
                .loadMarvelForEvents(series.id)
                .onErrorReturn( throwable ->  {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(events -> {
           return  new Reducer<InnerState, Effect>() {
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
}
