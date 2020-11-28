package com.example.controllers.storydetail;

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

public class StoryDetailViewModelDelegate extends BaseMviDelegate<State, StoryDetailViewModelDelegate.InnerState, Effect> {
    StoryDetailNetworkInterface networkInterface;
    ProcessedMarvelStory story;
    Runner runner = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState newInnerState = innerState.copy();
            newInnerState.loading = false;
            newInnerState.error = false;
            return withEffects(newInnerState , new Effect.ImageLoaded<>() );
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
    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(story, null, null, null, null, true, false));
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(innerState.story, innerState.characters, innerState.comics, innerState.series, innerState.events, innerState.loading, innerState.error, runner, clickListener);
    }
    public StoryDetailViewModelDelegate(StoryDetailNetworkInterface networkInterface, ProcessedMarvelStory story) {
        this.networkInterface = networkInterface;
        this.story = story;
        loadCharacters();
        loadComics();
        loadSeries();
        loadEvents();
    }

    public static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelStory story;
        List<ProcessedMarvelCharacter> characters;
        List<ProcessedMarvelComic> comics;
        List<ProcessedMarvelSeries> series;
        List<ProcessedMarvelEvent> events;

        public InnerState(ProcessedMarvelStory story, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelEvent> events, boolean loading, boolean error) {
            this.loading = loading;
            this.error = error;
            this.story = story;
            this.characters = characters;
            this.comics = comics;
            this.series = series;
            this.events = events;
        }

        public InnerState copy() {
            return new InnerState(story, characters, comics, series, events, loading, error);
        }
    }

    void loadCharacters() {
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelCharacter>> observable = networkInterface
                .loadMarvelCharactersForStory(story.id)
                .onErrorReturn(throwable -> {
                   return new ArrayList<>();
                });
        Observable<Reducer<InnerState,Effect>> reducerObservable = observable.map(characters -> {
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
    void loadSeries() {
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelSeries>> observable = networkInterface
                .loadMarvelSereisForStory(story.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState,Effect>> reducerObservable = observable.map(series -> {
            return new Reducer<InnerState, Effect>() {
                @Override
                public Change<InnerState, Effect> reduce(InnerState innerState) {
                    InnerState newInnerState = innerState.copy();
                    newInnerState.series = series;
                    return asChange(newInnerState);
                }
            };
        });
        enqueue(reducerObservable);
    }
    void loadComics () {
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelComic>> observable = networkInterface
                .loadMarvelComicsForStory(story.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState,Effect>> reducerObservable = observable.map(comics -> {
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
    void loadEvents() {
        if(networkInterface==null) return;
        Observable<List<ProcessedMarvelEvent>> observable = networkInterface
                .loadMarvelEventsForStory(story.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState,Effect>> reducerObservable = observable.map(events -> {
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
}
