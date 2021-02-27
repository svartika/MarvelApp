package com.perpy.controllers.seriesdetail

import android.view.View
import com.example.mviframework.BaseMviDelegate
import com.example.mviframework.Change
import com.example.mviframework.Reducer
import com.perpy.controllers.commons.*
import java.util.*

class SeriesDetailViewModelDelegate(val networkInterface: SeriesDetailNetworkInterface, val series: ProcessedMarvelSeries) : BaseMviDelegate<State, SeriesDetailViewModelDelegate.InnerState, Effect>() {
    var callbackRunner = dispatch { innerState ->
        val newInnerState = innerState.copy()
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
    var shareListener : () -> Unit = {
        enqueue { innerState -> withEffects(innerState, Effect.Share(innerState.series.urls.firstOrNull { it.type == "detail" }?.url)) }
    }
    override fun getInitialChange(): Change<InnerState, Effect> {
        return asChange(InnerState(series, null,null, null, null, null, true, false))
    }

    override fun mapState(innerState: InnerState): State {
        return State(series = innerState.series,
                urls = innerState.series.urls.map {
                    ProcessedURLItem(it.type, {this::onUrlLinkClicked.invoke(it.url)});
                },
                characters = innerState.characters, comics =  innerState.comics, stories =  innerState.stories, events =  innerState.events,
                loading = innerState.loading,  error = innerState.error, callbackRunner = callbackRunner, clickListener = clickListener, shareListener = shareListener,
                showShare = innerState.series.urls.firstOrNull {it.type=="detail"} != null
        )
    }

    class InnerState(var series: ProcessedMarvelSeries, var urls: List<ProcessedURLItem>?, var characters: List<ProcessedMarvelCharacter>?, var comics: List<ProcessedMarvelComic>?, var stories: List<ProcessedMarvelStory>?, var events: List<ProcessedMarvelEvent>?,
                     var loading: Boolean, var error: Boolean) {
        fun copy(): InnerState {
            return InnerState(series, urls, characters, comics, stories, events, loading, error)
        }
    }

    fun loadCharacters() {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadMarvelCharactersForSeries(series.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { characters: List<ProcessedMarvelCharacter> ->
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
                .loadMarvelComicsForSeries(series.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { comics: List<ProcessedMarvelComic> ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.comics = comics
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    fun loadStories() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelStoriesForSeries(series.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { stories: List<ProcessedMarvelStory> ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.stories = stories
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    fun loadEvents() {
        if (networkInterface == null) return
        val observable = networkInterface
                .loadMarvelForEvents(series.id)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable.map<Reducer<InnerState, Effect>> { events: List<ProcessedMarvelEvent> ->
            Reducer { innerState ->
                val newInnerState = innerState.copy()
                newInnerState.events = events
                asChange(newInnerState)
            }
        }
        enqueue(reducerObservable)
    }

    init {
        loadCharacters()
        loadComics()
        loadStories()
        loadEvents()
    }
}