package com.example.controllers.comicdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class ComicDetailViewModelFactory implements ViewModelProvider.Factory {
    ComicDetailViewModelDelegate delegate;
    public ComicDetailViewModelFactory(ComicDetailViewModelDelegate delegate) {
        this.delegate = delegate;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new ComicDetailViewModelImpl(delegate);
    }
}
