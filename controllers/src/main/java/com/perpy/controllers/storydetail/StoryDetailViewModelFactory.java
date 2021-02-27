package com.perpy.controllers.storydetail;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

public class StoryDetailViewModelFactory implements ViewModelProvider.Factory {
    StoryDetailViewModelDelegate delegate;
    public StoryDetailViewModelFactory(StoryDetailViewModelDelegate delegate) {
        this.delegate = delegate;
    }
    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new StoryDetailViewModelImpl(delegate);
    }
}
