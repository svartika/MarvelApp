package com.example.controllers.characterslist

import androidx.paging.PagedList
import com.example.controllers.commons.CardClickListener
import com.example.controllers.commons.ProcessedMarvelCharacter

data class State(
        val charactersList: PagedList<ProcessedMarvelCharacter>?,
        val searchStr: String?,
        val clickListener: CardClickListener<Any?>,
        val searchCallback: SearchTextChangedCallbackListener
)