package com.example.mviframework;


import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.BiFunction;
import io.reactivex.subjects.Subject;
import io.reactivex.subjects.UnicastSubject;

public abstract class BaseMviDelegate<State, InnerState, Effect> implements MviDelegate<State, Effect> {
    public abstract Change<InnerState, Effect> getInitialChange();

    Subject<Observable<Reducer<InnerState, Effect>>> innerchanges = UnicastSubject.create();

    @Override
    public Observable<Change<State, Effect>> getStateChanges() {
        return innerchanges.flatMap(reducerObservable -> reducerObservable).<Change<InnerState, Effect>>scan(getInitialChange(), new BiFunction<Change<InnerState, Effect>, Reducer, Change<InnerState, Effect>>() {
            @Override
            public Change<InnerState, Effect> apply(Change<InnerState, Effect> innerStateEffectChange, Reducer reducer) throws Exception {
                return reducer.reduce(innerStateEffectChange.state);
            }
        }).map(change -> {
            return new Change(mapState(change.state), change.effects);
        });
    }

    public abstract State mapState(InnerState innerState);

    protected void enqueue(Reducer<InnerState, Effect> reducer) {
        innerchanges.onNext(Observable.just(reducer));
    }

    protected void enqueue(Observable<Reducer<InnerState, Effect>> reducer) {
        innerchanges.onNext(reducer);
    }

    protected void enqueue(Single<Reducer<InnerState, Effect>> reducer) {
        enqueue(reducer.toObservable());
    }

    protected void enqueue(Maybe<Reducer<InnerState, Effect>> reducer) {
        enqueue(reducer.toObservable());
    }

    protected Change<InnerState, Effect> asChange(InnerState state) {
        return new Change(state, null);
    }

    protected Change<InnerState, Effect> withEffects(InnerState state, Effect... effects) {
        return new Change(state, Arrays.asList(effects));
    }

    protected Change<InnerState, Effect> withEffects(InnerState state, List<Effect> effects) {
        return new Change(state, effects);
    }

    protected Change<InnerState, Effect> withEffects(Change<InnerState, Effect> change, List<Effect> effects) {
        List<Effect> newEffects = Stream.concat(change.effects.stream(), effects.stream())
                .collect(Collectors.toList());
        return new Change(change.state, newEffects);
    }

    protected Runner dispatch(Reducer<InnerState, Effect> dispatchChange) {
        return new Runner() {
            @Override
            public void run() {
                innerchanges.onNext(Observable.just(new Reducer<InnerState, Effect>() {
                    @Override
                    public Change<InnerState, Effect> reduce(InnerState innerState) {
                        Change<InnerState, Effect> change = dispatchChange.reduce(innerState);
                        if(change!=null) {
                            return change;
                        } else {
                            return asChange(innerState);
                        }
                    }
                }));
            }
        };
    }



}
