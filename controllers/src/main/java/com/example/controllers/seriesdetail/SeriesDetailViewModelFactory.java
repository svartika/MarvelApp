package com.example.controllers.seriesdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class SeriesDetailViewModelFactory implements ViewModelProvider.Factory {
    SeriesDetailViewModelDelegate delegate;
    public SeriesDetailViewModelFactory(SeriesDetailViewModelDelegate delegate) {
        this.delegate = delegate;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new SeriesDetailViewModelImpl(delegate);
    }
}
