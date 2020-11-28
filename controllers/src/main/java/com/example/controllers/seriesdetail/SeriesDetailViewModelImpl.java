package com.example.controllers.seriesdetail;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class SeriesDetailViewModelImpl extends AndroidMviViewModel<State, Effect> implements SeriesDetailViewModel{
    SeriesDetailViewModelDelegate delegate;
    public SeriesDetailViewModelImpl(SeriesDetailViewModelDelegate delegate) {
        super(new BaseMviViewModel(delegate));
    }
}
