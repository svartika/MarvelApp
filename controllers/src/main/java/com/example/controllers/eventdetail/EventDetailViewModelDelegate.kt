package com.example.controllers.eventdetail

import android.view.View
import com.example.controllers.commons.*
import com.example.mviframework.BaseMviDelegate
import com.example.mviframework.Change
import com.example.mviframework.Reducer
import java.util.*

class EventDetailViewModelDelegate(val networkInterface: EventDetailNetworkInterface, val event: ProcessedMarvelEvent) : BaseMviDelegate<State, EventDetailViewModelDelegate.InnerState, Effect>() {
    var runner = dispatch { innerState ->
        val newInnerState = innerState!!.copy()
        newInnerState.loading = false
        newInnerState.error = false
        withEffects(newInnerState, Effect.ImageLoaded<Any>())
    }
    var clickListener: CardClickListener<*> = object : CardClickListener<ProcessedMarvelItemBase> {
        override fun invoke(view: View, item: ProcessedMarvelItemBase) {
            enqueue { innerState -> withEffects(innerState, Effect.CardClickedEffect(view, item)) }
        }
    }
    fun onUrlLinkClicked(url: String) {
        enqueue {
            withEffects(it, Effect.OpenUrl(url))
        }
    }
    override fun getInitialChange(): Change<InnerState, Effect> {
        return asChange(InnerState(event, null, null, null, null, null, true, false))
    }

    override fun mapState(innerState: InnerState): State {
        return State(innerState.event,
                innerState.event.urls.map {
                    ProcessedURLItem(it.type, {this::onUrlLinkClicked.invoke(it.url)});
                },
                innerState.characters, innerState.comics, innerState.series, innerState.stories, innerState.loading, innerState.error, runner, clickListener)
    }

    class InnerState(var event: ProcessedMarvelEvent, var urls: List<ProcessedURLItem>?, var characters: List<ProcessedMarvelCharacter?>?, var comics: List<ProcessedMarvelComic?>?, var series: List<ProcessedMarvelSeries?>?, var stories: List<ProcessedMarvelStory?>?, var loading: Boolean, var error: Boolean) {
        fun copy(): InnerState {
            return InnerState(event, urls, characters, comics, series, stories, loading, error)
        }
    }

    fun loadCharacters() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelCharacterForEvent(event.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { characters: List<ProcessedMarvelCharacter?>? ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.characters = characters
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    fun loadComics() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelComicForEvent(event.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { comics: List<ProcessedMarvelComic?>? ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.comics = comics
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    fun loadSeries() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelSeriesForEvent(event.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { series: List<ProcessedMarvelSeries?>? ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.series = series
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    fun loadStories() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelStoriesForEvent(event.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { stories: List<ProcessedMarvelStory?>? ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.stories = stories
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    init {
        loadCharacters()
        loadComics()
        loadSeries()
        loadStories()
    }
}