package com.perpy.controllers.characterslist

import androidx.paging.PagedList
import com.perpy.controllers.commons.CardClickListener
import com.perpy.controllers.commons.ProcessedMarvelCharacter

data class State(
        val charactersList: PagedList<ProcessedMarvelCharacter>?,
        val searchStr: String?,
        val clickListener: CardClickListener<Any?>,
        val searchCallback: SearchTextChangedCallbackListener
)