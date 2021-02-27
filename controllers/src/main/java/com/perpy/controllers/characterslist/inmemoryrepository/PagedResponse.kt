package com.perpy.controllers.characterslist.inmemoryrepository

import androidx.paging.PagedList
import io.reactivex.Observable

data class PagedResponse<T>(
        val list: Observable<PagedList<T>>
)