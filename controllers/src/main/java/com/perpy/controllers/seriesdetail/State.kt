package com.perpy.controllers.seriesdetail

import com.example.mviframework.Runner
import com.perpy.controllers.commons.*

class State(
        val loading: Boolean,
        val error: Boolean,
        val series: ProcessedMarvelSeries,
        val urls: List<ProcessedURLItem>?,
        val characters: List<ProcessedMarvelCharacter>?,
        val comics: List<ProcessedMarvelComic>?,
        val stories: List<ProcessedMarvelStory>?,
        val events: List<ProcessedMarvelEvent>?,
        val callbackRunner: Runner,
        val showShare: Boolean,
        val clickListener: CardClickListener<*>,
        val shareListener: () -> Unit
) {
}