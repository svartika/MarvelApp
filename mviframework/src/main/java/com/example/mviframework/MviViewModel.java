package com.example.mviframework;

import io.reactivex.Observable;

public interface MviViewModel<State, Effect> {
    Observable<State> getState();

    Observable<Effect> getEffect();

    void start();

    void stop();
}
