package com.perpy.controllers.eventdetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class EventDetailViewModelFactory implements ViewModelProvider.Factory {
    EventDetailViewModelDelegate delegate;

    public EventDetailViewModelFactory(EventDetailViewModelDelegate delegate) {
        this.delegate = delegate;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new EventDetailViewModelImpl(delegate) ;
    }
}
