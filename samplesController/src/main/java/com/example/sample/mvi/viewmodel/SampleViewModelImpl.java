package com.example.sample.mvi.viewmodel;

import com.example.mviframework.AndroidMviViewModel;
import com.example.mviframework.BaseMviViewModel;

public class SampleViewModelImpl extends AndroidMviViewModel<State, Effect> implements SampleViewModel {

    public SampleViewModelImpl(SampleViewModelDelegate sampleViewModelDelegate) {
        super(new BaseMviViewModel(sampleViewModelDelegate));
    }
}
