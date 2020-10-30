package com.example.controllers.characterdetail;

import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.mviframework.Runner;

import java.util.Objects;

public class State {
    boolean loading;
    boolean error;
    ProcessedMarvelCharacter character;
    Runner callbackRunner;

    State(ProcessedMarvelCharacter character, boolean loading, boolean error, Runner callbackRunner) {
        this.character = character;
        this.loading = loading;
        this.error = error;

        this.callbackRunner = callbackRunner;
    }

    public ProcessedMarvelCharacter getCharacter() {
        return character;
    }

    public boolean isLoading() {
        return loading;
    }

    public boolean isError() {
        return error;
    }

    public Runner getCallbackRunner() {
        return callbackRunner;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return isLoading() == state.isLoading() &&
                isError() == state.isError() &&
                getCharacter().equals(state.getCharacter()) &&
                getCallbackRunner().equals(state.getCallbackRunner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getCharacter(), getCallbackRunner());
    }
}
