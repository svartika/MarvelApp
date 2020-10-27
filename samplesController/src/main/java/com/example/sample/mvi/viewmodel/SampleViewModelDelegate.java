package com.example.sample.mvi.viewmodel;

import com.example.mviframework.BaseMviDelegate;
import com.example.mviframework.Change;
import com.example.mviframework.Reducer;
import com.example.mviframework.Runner;

public class SampleViewModelDelegate extends BaseMviDelegate<State, SampleViewModelDelegate.InnerState, Effect> {
    int dependency;

    Runner clickToIncrement = dispatch(new Reducer<InnerState, Effect>() {
        @Override
        public Change<InnerState, Effect> reduce(InnerState innerState) {
            InnerState innerState1 =  innerState.copy();
            innerState1.identifier = innerState1.identifier+1;
            return withEffects(innerState1, new Effect.ToastMe());
        }
    });

    public SampleViewModelDelegate(int dependency) {
        this.dependency = dependency;
    }

    @Override
    public Change<InnerState, Effect> getInitialChange() {
        return asChange(clear);
    }

    @Override
    public State mapState(InnerState innerState) {
        return new State(String.valueOf(innerState.identifier), String.valueOf(dependency), clickToIncrement);
    }

    static class InnerState {
        int identifier;

        InnerState(int identifier) {
            this.identifier = identifier;
        }

        InnerState copy() {
            return new InnerState(identifier);
        }

    }

    static InnerState clear = new InnerState(0);
}
