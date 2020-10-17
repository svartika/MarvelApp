package com.example.mviframework;


import io.reactivex.Observable;

public interface MviDelegate<State, Effect> {
    Observable<Change<State, Effect>> getStateChanges();
}
