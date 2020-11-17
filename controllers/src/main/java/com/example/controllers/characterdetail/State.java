package com.example.controllers.characterdetail;

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.mviframework.Runner;

import java.util.List;
import java.util.Objects;

public class State {
    boolean loading;
    boolean error;
    ProcessedMarvelCharacter character;
    List<ProcessedMarvelComic> comics;
    Runner callbackRunner;

    State(ProcessedMarvelCharacter character, List<ProcessedMarvelComic> comics, boolean loading, boolean error, Runner callbackRunner) {
        this.character = character;
        this.comics = comics;
        this.loading = loading;
        this.error = error;

        this.callbackRunner = callbackRunner;
    }

    @Override
    public String toString() {
        return "State("+hashCode()+"){" +
                "loading=" + loading +
                ", error=" + error +
                ", character=" + character +
                ", comics=" + comics +
                ", callbackRunner=" + callbackRunner +
                '}';
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

    public List<ProcessedMarvelComic> getComics() {
        return comics;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return isLoading() == state.isLoading() &&
                isError() == state.isError() &&
                getCharacter().equals(state.getCharacter()) &&
                getCallbackRunner().equals(state.getCallbackRunner()) &&
                compareLists(comics,state.comics);
    }

    boolean compareLists(List<ProcessedMarvelComic> first, List<ProcessedMarvelComic> second) {
        if(first==null && second==null) return true;
        else if(first==null || second==null) return false;
        else if(first.size()!=second.size()) return false;
        else {
            boolean ret = true;
            for(ProcessedMarvelComic elem1: first) {
                for(ProcessedMarvelComic elem2: second) {
                    if(elem1.id!=elem2.id) {
                        ret = false;
                    }
                }
            }
            return ret;
         }
    }
    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getCharacter(), getCallbackRunner());
    }
}
