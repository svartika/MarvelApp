package com.example.mviframework;

import androidx.lifecycle.LiveData;

public interface LiveDataViewModel<State, Effect> {
    LiveData<State> getState();

    LiveData<Effect> getEffect();
}
