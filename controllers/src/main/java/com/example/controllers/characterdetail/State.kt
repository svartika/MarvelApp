package com.example.controllers.characterdetail

import com.example.controllers.commons.*
import com.example.mviframework.Runner

data class State(
        val loading: Boolean,
        val error: Boolean,
        val character: ProcessedMarvelCharacter,
        val urls: List<ProcessedURLItem>?,
        val comics: List<ProcessedMarvelComic>?,
        val series: List<ProcessedMarvelSeries>?,
        val stories: List<ProcessedMarvelStory>?,
        val events: List<ProcessedMarvelEvent>?,
        val callbackRunner: Runner,
        val showShare: Boolean,
        val clickListener: CardClickListener<*>,
        val shareListener: () -> Unit
) {
    /* override fun equals(o: Any): Boolean {
         if (this === o) return true
         if (o !is State) return false
         val state = o
         val ret = loading == state.loading && error == state.error && character == state.character && callbackRunner === state.callbackRunner &&
                 Utils.compareLists(comics, state.comics) &&
                 Utils.compareLists(series, state.series) &&
                 Utils.compareLists(stories, state.stories) &&
                 Utils.compareLists(events, state.events) && clickListener === state.clickListener
         Log.d("Vartika", "Character Detail state comparison: $ret")
         return ret
     }*/

    /* override fun hashCode(): Int {
         return Objects.hash(isLoading(), isError(), getCharacter(), getComics(), getSeries(), getStories(), getEvents(), getCallbackRunner(), getClickListener(), getShareListener())
     }*/
}
