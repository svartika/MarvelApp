package com.example.mviframework;

import io.reactivex.Observable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.observables.ConnectableObservable;

public class BaseMviViewModel<State, Effect> implements MviViewModel<State, Effect> {
    MviDelegate mviDelegate;

    ConnectableObservable<Change<State, Effect>> changes;
    CompositeDisposable compositedisposable = new CompositeDisposable();
    Observable<State> state;
    Observable<Effect> effect;

    public BaseMviViewModel(MviDelegate mviDelegate) {
        this.mviDelegate = mviDelegate;
        changes = mviDelegate.getStateChanges().publish();
        state = changes.map(change-> {return change.state;});
        effect = changes.flatMapIterable(change-> {return change.effects;});
    }


    @Override
    public Observable<State> getState() {
        return state;
    }

    @Override
    public Observable<Effect> getEffect() {
        return effect;
    }

    @Override
    public void start() {
        Disposable disposable = changes.connect();
        compositedisposable.add(disposable);
    }

    @Override
    public void stop() {
        compositedisposable.dispose();
    }
}
