package com.perpy.controllers.characterslist.inmemoryrepository

import androidx.paging.PagedList
import androidx.paging.RxPagedListBuilder
import com.perpy.controllers.characterslist.CharactersListNetworkInterface
import com.perpy.controllers.commons.ProcessedMarvelCharacter
import javax.inject.Inject

public class CharactersListRepositoryImpl @Inject constructor(val api: CharactersListNetworkInterface) : CharactersListRepository {

    override fun searchCharacter(searchKeyword: String): PagedResponse<ProcessedMarvelCharacter> {
        val dataSourceFactory = CharactersListDataSourceFactory(searchKeyword, api)
        val pagedList = RxPagedListBuilder(dataSourceFactory,
                PagedList.Config.Builder().setInitialLoadSizeHint(INITIAL_LOAD_SIZE).setPageSize(PAGE_SIZE).setEnablePlaceholders(true).build()).buildObservable()
        return PagedResponse(pagedList)
    }

    //have data source and network interface here
    companion object {
        const val PAGE_SIZE = 20
        const val INITIAL_LOAD_SIZE = 30
    }
}

interface CharactersListRepository {
    fun searchCharacter(searchKeyword: String): PagedResponse<ProcessedMarvelCharacter>
}