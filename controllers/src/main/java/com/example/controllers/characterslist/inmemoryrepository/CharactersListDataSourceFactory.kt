package com.example.controllers.characterslist.inmemoryrepository

import androidx.paging.DataSource
import com.example.controllers.characterslist.CharactersListNetworkInterface
import com.example.controllers.commons.ProcessedMarvelCharacter
import java.lang.Integer
class CharactersListDataSourceFactory(val searchTerm: String, val api: CharactersListNetworkInterface) : DataSource.Factory<Integer, ProcessedMarvelCharacter>() {
    override fun create(): DataSource<Integer, ProcessedMarvelCharacter> {
        val source = PositionKeyedCharactersListDataSource(searchTerm, api)
        return source as DataSource<Integer, ProcessedMarvelCharacter>
    }
}