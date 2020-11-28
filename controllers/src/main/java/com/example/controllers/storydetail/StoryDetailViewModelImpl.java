package com.example.controllers.storydetail;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class StoryDetailViewModelImpl extends AndroidMviViewModel<State, Effect> implements StoryDetailViewModel {
    StoryDetailViewModelDelegate delegate;
    public StoryDetailViewModelImpl(StoryDetailViewModelDelegate delegate) {
        super(new BaseMviViewModel(delegate));
    }
}
