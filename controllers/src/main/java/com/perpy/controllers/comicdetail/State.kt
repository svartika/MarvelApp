package com.perpy.controllers.comicdetail

import com.example.mviframework.Runner
import com.perpy.controllers.commons.*

data class State(
        val loading: Boolean,
        val error: Boolean,
        val comic: ProcessedMarvelComic,
        val urls: List<ProcessedURLItem>?,
        val characters: List<ProcessedMarvelCharacter>?,
        val series: List<ProcessedMarvelSeries>?,
        val stories: List<ProcessedMarvelStory>?,
        val events: List<ProcessedMarvelEvent>?,
        val callbackRunner: Runner,
        val showShare: Boolean,
        val clickListener: CardClickListener<*>,
        val shareListener: () -> Unit
)
