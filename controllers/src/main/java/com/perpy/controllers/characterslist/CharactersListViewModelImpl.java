package com.perpy.controllers.characterslist;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class CharactersListViewModelImpl extends AndroidMviViewModel<State, Effect> implements CharactersListViewModel{
    CharactersListViewModelImpl(CharactersListModelDelegate charactersListModelDelegate) {
        super(new BaseMviViewModel(charactersListModelDelegate));
    }
}
