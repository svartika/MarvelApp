package com.perpy.controllers.characterslist.inmemoryrepository

import androidx.paging.DataSource
import com.perpy.controllers.characterslist.CharactersListNetworkInterface
import com.perpy.controllers.commons.ProcessedMarvelCharacter

class CharactersListDataSourceFactory(val searchTerm: String, val api: CharactersListNetworkInterface) : DataSource.Factory<Integer, ProcessedMarvelCharacter>() {
    override fun create(): DataSource<Integer, ProcessedMarvelCharacter> {
        val source = PositionKeyedCharactersListDataSource(searchTerm, api)
        return source as DataSource<Integer, ProcessedMarvelCharacter>
    }
}