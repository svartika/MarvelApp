package com.example.controllers.eventdetail;

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

public class EventDetailViewModelDelegate extends BaseMviDelegate<State, EventDetailViewModelDelegate.InnerState, Effect> {
    EventDetailNetworkInterface networkInterface;
    ProcessedMarvelEvent event;

    Runner runner = dispatch(new Reducer<InnerState, Effect>() {
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

    public EventDetailViewModelDelegate(EventDetailNetworkInterface networkInterface, ProcessedMarvelEvent event) {
        this.networkInterface = networkInterface;
        this.event = event;
        loadCharacters();
        loadComics();
        loadSeries();
        loadStories();
    }

    @Override
    public Change<EventDetailViewModelDelegate.InnerState, Effect> getInitialChange() {
        return asChange(new InnerState(event, null, null, null, null, true, false));
    }

    @Override
    public State mapState(EventDetailViewModelDelegate.InnerState innerState) {
        return new State(innerState.event, innerState.characters, innerState.comics, innerState.series, innerState.stories, innerState.loading, innerState.error, runner, clickListener);
    }

    static class InnerState {
        boolean loading;
        boolean error;
        ProcessedMarvelEvent event;
        List<ProcessedMarvelCharacter> characters;
        List<ProcessedMarvelComic> comics;
        List<ProcessedMarvelSeries> series;
        List<ProcessedMarvelStory> stories;

        public InnerState(ProcessedMarvelEvent event, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, boolean loading, boolean error) {
            this.loading = loading;
            this.error = error;
            this.event = event;
            this.characters = characters;
            this.comics = comics;
            this.series = series;
            this.stories = stories;
        }

        public InnerState copy() {
            return new InnerState(event, characters, comics, series, stories, loading, error);
        }
    }

    void loadCharacters() {
        if (networkInterface == null) return;
        Observable<List<ProcessedMarvelCharacter>> observable = networkInterface
                .loadMarvelCharacterForEvent(event.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(characters -> {
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

    void loadComics() {
        if (networkInterface == null) return;
        Observable<List<ProcessedMarvelComic>> observable = networkInterface
                .loadMarvelComicForEvent(event.id)
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

    void loadSeries() {
        if (networkInterface == null) return;
        Observable<List<ProcessedMarvelSeries>> observable = networkInterface
                .loadMarvelSeriesForEvent(event.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(series -> {
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

    void loadStories() {
        if (networkInterface == null) return;
        Observable<List<ProcessedMarvelStory>> observable = networkInterface
                .loadMarvelStoriesForEvent(event.id)
                .onErrorReturn(throwable -> {
                    return new ArrayList<>();
                });
        Observable<Reducer<InnerState, Effect>> reducerObservable = observable.map(stories -> {
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

}
