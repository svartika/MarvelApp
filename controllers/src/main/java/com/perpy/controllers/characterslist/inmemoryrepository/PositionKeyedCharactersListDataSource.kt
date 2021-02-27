package com.perpy.controllers.characterslist.inmemoryrepository

import android.util.Log
import androidx.paging.PositionalDataSource
import com.perpy.controllers.characterslist.CharactersListNetworkInterface
import com.perpy.controllers.commons.ProcessedMarvelCharacter
import io.reactivex.functions.Consumer

class PositionKeyedCharactersListDataSource (
        private val searchTerm: String,
        val api: CharactersListNetworkInterface
) : PositionalDataSource<ProcessedMarvelCharacter>()  {


    override fun loadRange(params: LoadRangeParams, callback: LoadRangeCallback<ProcessedMarvelCharacter>) {

        api.searchCharacter(searchTerm, params.startPosition, params.loadSize).subscribe(
                Consumer {
                    callback.onResult(it.list)
                },
                { Log.d("ItemKeyedCharactersListDataSource", it.localizedMessage) }
        )

    }

    override fun loadInitial(params: LoadInitialParams, callback: LoadInitialCallback<ProcessedMarvelCharacter>) {
        api.searchCharacter(searchTerm, 0, params.requestedLoadSize).subscribe(
                Consumer {
                    callback.onResult(it.list, 0, it.total)
                },
                { Log.d("ItemKeyedCharactersListDataSource", it.localizedMessage) }
        )
    }
}