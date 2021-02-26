package com.example.controllers.characterdetail

import android.util.Log
import android.view.View
import com.example.controllers.commons.*
import com.example.mviframework.BaseMviDelegate
import com.example.mviframework.Change
import com.example.mviframework.Reducer
import java.util.*

class CharacterDetailViewModelDelegate(val networkInterface: CharacterDetailNetworkInterface?, val character: ProcessedMarvelCharacter) : BaseMviDelegate<State, CharacterDetailViewModelDelegate.InnerState, Effect>() {
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

    var shareListener: () -> Unit = {
        enqueue { innerState -> withEffects(innerState, Effect.Share(innerState.character.urls.firstOrNull { it.type == "detail" }?.url)) }
    }

    override fun getInitialChange(): Change<InnerState, Effect?> {
        return asChange(InnerState(character, null, null, null, null, true, false))
    }

    override fun mapState(innerState: InnerState): State {
        val state = State(character = innerState.character,
                urls = innerState.character.urls.map {
                    ProcessedURLItem(it.type, { this::onUrlLinkClicked.invoke(it.url) });
                },
                comics = innerState.comics,
                series = innerState.series,
                stories = innerState.stories, events = innerState.events, loading = innerState.loading, error = innerState.error, callbackRunner = callbackRunner,
                clickListener = cardClickListener, shareListener = shareListener,
                showShare = innerState.character.urls.firstOrNull { it.type == "detail" } != null)
        Log.d("Vartika", "Character Detail mapState: $state")
        return state
    }

    class InnerState(var character: ProcessedMarvelCharacter, var comics: List<ProcessedMarvelComic>?, var series: List<ProcessedMarvelSeries>?, var stories: List<ProcessedMarvelStory>?, var events: List<ProcessedMarvelEvent>?, var loading: Boolean, var error: Boolean) {
        fun copy(): InnerState {
            return InnerState(character, comics, series, stories, events, loading, error)
        }
    }

    private fun loadComics(characterId: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadCharacterComics(characterId)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable
                .map<Reducer<InnerState, Effect>> { comics: List<ProcessedMarvelComic> ->
                    Log.d("Vartika3", "Reducer1: $comics")
                    Reducer { innerState ->
                        Log.d("Vartika3", "Reducer2: $comics")
                        val newInnerState = innerState.copy()
                        newInnerState.comics = comics
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadSeries(characterId: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadCharacterSeries(characterId)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable
                .map<Reducer<InnerState, Effect>> { series: List<ProcessedMarvelSeries> ->
                    Log.d("Vartika3", "Reducer1: series $series")
                    Reducer { innerState ->
                        Log.d("Vartika3", "Reducer2: series $series")
                        val newInnerState = innerState.copy()
                        newInnerState.series = series
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadStories(characterId: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadCharacterStories(characterId)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable
                .map<Reducer<InnerState, Effect>> { stories: List<ProcessedMarvelStory> ->
                    Log.d("Vartika3", "Reducer1: stories -> $stories")
                    Reducer { innerState ->
                        Log.d("Vartika3", "Reducer2: stories -> $stories")
                        val newInnerState = innerState.copy()
                        newInnerState.stories = stories
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    private fun loadEvents(characterId: Int) {
        if (networkInterface == null) {
            return
        }
        val observable = networkInterface
                .loadCharacterEvents(characterId)
                .onErrorReturn { throwable: Throwable? -> ArrayList() }
        val reducerObservable = observable
                .map<Reducer<InnerState, Effect>> { events: List<ProcessedMarvelEvent> ->
                    Log.d("Vartika3", "Reducer1: events -> $events")
                    Reducer { innerState ->
                        Log.d("Vartika3", "Reducer2: events -> $events")
                        val newInnerState = innerState.copy()
                        newInnerState.events = events
                        asChange(newInnerState)
                    }
                }
        enqueue(reducerObservable)
    }

    init {
        loadComics(character.id)
        loadSeries(character.id)
        loadStories(character.id)
        loadEvents(character.id)
    }
}