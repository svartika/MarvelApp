package com.example.mviframework;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;


import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class AndroidMviViewModel<State, Effect> extends ViewModel implements LiveDataViewModel<State, Effect> {

    CompositeDisposable compositeDisposable = new CompositeDisposable();
    MviViewModel<State, Effect> baseViewModel;
    ControlledLiveData<Effect> bufferedEffects;
    MutableLiveData<State> mutableState = new MutableLiveData<>();

    public AndroidMviViewModel(MviViewModel<State, Effect> baseViewModel) {
        this.baseViewModel = baseViewModel;
        bufferedEffects = new ControlledLiveData<>(baseViewModel.getEffect());
        compositeDisposable.add(bufferedEffects);
        Disposable disposable = baseViewModel.getState().distinctUntilChanged().subscribe(state -> {
            mutableState.postValue(state);
        });
        compositeDisposable.add(disposable);
        baseViewModel.start();
    }




    @Override
    protected void onCleared() {
        compositeDisposable.clear();
        super.onCleared();
        baseViewModel.stop();
    }

    @Override
    public LiveData<State> getState() {
        return mutableState;
    }

    @Override
    public LiveData<Effect> getEffect() {
        return bufferedEffects;
    }
}
