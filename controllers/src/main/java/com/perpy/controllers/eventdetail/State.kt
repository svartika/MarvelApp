package com.perpy.controllers.eventdetail

import com.example.mviframework.Runner
import com.perpy.controllers.commons.*

class State (
        val loading: Boolean,
        val error: Boolean,
        val event: ProcessedMarvelEvent,
        val urls: List<ProcessedURLItem>?,
        val characters: List<ProcessedMarvelCharacter>?,
        val comics: List<ProcessedMarvelComic>?,
        val series: List<ProcessedMarvelSeries>?,
        val stories: List<ProcessedMarvelStory>?,
        val callbackRunner: Runner,
        val showShare: Boolean,
        val clickListener: CardClickListener<*>,
        val shareListener: () -> Unit
) {
}