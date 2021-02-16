package com.example.controllers.characterslist.inmemoryrepository

import android.util.Log
import androidx.paging.ItemKeyedDataSource
import com.example.controllers.characterslist.CharactersListNetworkInterface
import com.example.controllers.commons.ProcessedMarvelCharacter
import io.reactivex.functions.Consumer

class ItemKeyedCharactersListDataSource(
        private val searchTerm: String,
        val api: CharactersListNetworkInterface
) : ItemKeyedDataSource<String, ProcessedMarvelCharacter>() {
    // @Inject
    //lateinit var api: CharactersListNetworkInterface
    override fun getKey(item: ProcessedMarvelCharacter): String = item.id.toString()

    override fun loadInitial(params: LoadInitialParams<String>, callback: LoadInitialCallback<ProcessedMarvelCharacter>) {
        api.searchCharacter(searchTerm,
                0,
                params.requestedLoadSize).subscribe(
                Consumer {
                    callback.onResult(it.list)
                },
                { Log.d("ItemKeyedCharactersListDataSource", it.localizedMessage) }
        )
    }

    override fun loadAfter(params: LoadParams<String>, callback: LoadCallback<ProcessedMarvelCharacter>) {
        api.searchCharactersFrom(searchTerm,
                params.key.toInt(),
                params.requestedLoadSize).subscribe(
                Consumer {
                    callback.onResult(it.list)
                },
                { android.util.Log.d("ItemKeyedCharactersListDataSource", it.localizedMessage) }
        )
    }

    override fun loadBefore(params: LoadParams<String>, callback: LoadCallback<ProcessedMarvelCharacter>) {

    }
}