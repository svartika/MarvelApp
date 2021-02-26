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
}
