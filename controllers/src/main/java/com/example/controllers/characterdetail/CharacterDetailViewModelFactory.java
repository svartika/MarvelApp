package com.example.controllers.characterdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class CharacterDetailViewModelFactory implements ViewModelProvider.Factory {

    CharacterDetailViewModelDelegate delegate;
    public CharacterDetailViewModelFactory(CharacterDetailViewModelDelegate delegate) {
        this.delegate = delegate;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new CharacterDetailViewModelImpl(delegate);
    }
}
