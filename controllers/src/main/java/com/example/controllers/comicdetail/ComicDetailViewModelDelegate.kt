package com.example.controllers.comicdetail

import android.util.Log
import android.view.View
import com.example.controllers.commons.*
import com.example.mviframework.BaseMviDelegate
import com.example.mviframework.Change
import com.example.mviframework.Reducer
import io.reactivex.Observable
import java.util.*

class ComicDetailViewModelDelegate(val networkInterface: ComicDetailNetworkInterface, val comic: ProcessedMarvelComic) : BaseMviDelegate<State, ComicDetailViewModelDelegate.InnerState, Effect>() {
    var cardClickListener: CardClickListener<*> = object : CardClickListener<ProcessedMarvelItemBase> {
        override fun invoke(view: View, item: ProcessedMarvelItemBase) {
            enqueue { innerState -> withEffects(innerState, Effect.ClickCardEffect(view, item)) }
        }
    }
    var callbackRunner = dispatch { innerState ->
        val newInnerState = innerState.copy()
        newInnerState.loading = false
        newInnerState.error = false
        withEffects(newInnerState, Effect.ImageLoaded<Any?>())
    }
    fun onUrlLinkClicked(url: String) {
        enqueue {
            withEffects(it, Effect.OpenUrl(url))
        }
    }

    private fun loadEvents(id: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadEventsForComic(comic.id)
                .onErrorReturn { throwable: Throwable -> ArrayList() }
        val reducerObservable: Observable<Reducer<InnerState, Effect>> = observable
                .map { events: List<ProcessedMarvelEvent> ->
                    Reducer { innerState ->
                        val newInnerState = innerState.copy()
                        newInnerState.events = events
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadStories(id: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadStoriesForComic(comic.id)
                .onErrorReturn { throwable: Throwable -> ArrayList() }
        val reducerObservable: Observable<Reducer<InnerState, Effect>> = observable
                .map { stories: List<ProcessedMarvelStory> ->
                    Reducer { innerState ->
                        val newInnerState = innerState.copy()
                        newInnerState.stories = stories
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadSeries(id: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadSeriesForComic(comic.id)
                .onErrorReturn { throwable: Throwable -> ArrayList() }
        val reducerObservable: Observable<Reducer<InnerState, Effect>> = observable
                .map { series: List<ProcessedMarvelSeries> ->
                    Reducer { innerState -> //Log.d("Vartika3", "Reducer2: series "+series);
                        val newInnerState = innerState.copy()
                        newInnerState.series = series
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadCharacters(id: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadCharactersForComic(comic.id)
                .onErrorReturn { throwable: Throwable -> ArrayList() }
        val reducerObservable: Observable<Reducer<InnerState, Effect>> = observable
                .map { characters: List<ProcessedMarvelCharacter> ->
                    Reducer { innerState -> //Log.d("Vartika3", "Reducer2: series "+series);
                        val newInnerState = innerState.copy()
                        newInnerState.characters = characters
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    override fun getInitialChange(): Change<InnerState, Effect> {
        return asChange(InnerState(comic, null, null, null, null, null, true, false))
    }

    override fun mapState(innerState: InnerState): State {
        val state = State(
                innerState.comic,
                innerState.comic.urls.map {
                    ProcessedURLItem(it.type, {this::onUrlLinkClicked.invoke(it.url)});
                },
                innerState.characters,
                innerState.series,
                innerState.stories,
                innerState.events,
                innerState.loading,
                innerState.error,
                callbackRunner,
                cardClickListener)
        Log.d("Vartika", "Comic Detail mapState: $state")
        return state
    }

    class InnerState(var comic: ProcessedMarvelComic, var urls: List<ProcessedURLItem>?, var characters: List<ProcessedMarvelCharacter>?, var series: List<ProcessedMarvelSeries>?, var stories: List<ProcessedMarvelStory>?, var events: List<ProcessedMarvelEvent>?, var loading: Boolean, var error: Boolean) {
        fun copy(): InnerState {
            return InnerState(comic, urls, characters, series, stories, events, loading, error)
        }
    }

    init {
        loadCharacters(comic.id)
        loadSeries(comic.id)
        loadStories(comic.id)
        loadEvents(comic.id)
    }
}