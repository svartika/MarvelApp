package com.perpy.controllers.characterslist;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CharactersListViewModelFactory implements ViewModelProvider.Factory {
    CharactersListModelDelegate delegate;

    public CharactersListViewModelFactory(CharactersListModelDelegate delegate) {
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) (new CharactersListViewModelImpl(delegate));
    }

}
