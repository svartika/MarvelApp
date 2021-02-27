package com.perpy.controllers.eventdetail;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class EventDetailViewModelImpl extends AndroidMviViewModel<State, Effect> implements EventDetailViewModel {

    public EventDetailViewModelImpl(EventDetailViewModelDelegate delegate) {
        super(new BaseMviViewModel(delegate));
    }
}
