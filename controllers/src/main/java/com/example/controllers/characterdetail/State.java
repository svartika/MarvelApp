package com.example.controllers.characterdetail;

import android.util.Log;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.mviframework.Runner;

import java.util.List;
import java.util.Objects;

public class State {
    boolean loading;
    boolean error;
    ProcessedMarvelCharacter character;
    List<ProcessedMarvelComic> comics;
    List<ProcessedMarvelSeries> series;
    List<ProcessedMarvelStory> stories;
    List<ProcessedMarvelEvent> events;
    Runner callbackRunner;
    public CardClickListener clickListener;

    State(ProcessedMarvelCharacter character, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error, Runner callbackRunner, CardClickListener clickListener) {
        this.character = character;
        this.comics = comics;
        this.loading = loading;
        this.error = error;
        this.series = series;
        this.stories = stories;
        this.events = events;
        this.callbackRunner = callbackRunner;
        this.clickListener = clickListener;
    }

    @Override
    public String toString() {
        return "State("+hashCode()+"){" +
                "loading=" + loading +
                ", error=" + error +
                ", character=" + character +
                ", comics=" + comics +
                ", series=" + series +
                ", stories=" + stories +
                ", events=" + events +
                ", callbackRunner=" + callbackRunner +
                ", clickListener=" + clickListener +
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
    public List<ProcessedMarvelSeries> getSeries() {
        return series;
    }
    public List<ProcessedMarvelStory> getStories() {
        return stories;
    }
    public List<ProcessedMarvelEvent> getEvents() {
        return events;
    }
    public CardClickListener getClickListener() {
        return clickListener;
    }
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        boolean ret = isLoading() == state.isLoading() &&
                isError() == state.isError() &&
                getCharacter().equals(state.getCharacter()) &&
                getCallbackRunner()==state.getCallbackRunner() &&
                compareLists(comics,state.comics) &&
                compareLists(series, state.series) &&
                compareLists(stories, state.stories) &&
                compareLists(events, state.events) &&
                getClickListener()==state.getClickListener();
        Log.d("Vartika", "Character Detail state comparison: " + ret);
        //Log.d("Vartika", "Character Detail value 1: " + ((State) o).toString());
        //Log.d("Vartika", "Character Detail value 2: " + this.toString());
        return ret;
    }

    boolean compareLists(List<? extends ProcessedMarvelItemBase> first, List<? extends  ProcessedMarvelItemBase> second) {
        if(first==null && second==null) return true;
        else if(first==null || second==null) return false;
        else if(first.size()!=second.size()) return false;
        else {
            boolean ret = true;
            for(int i = 0; i < first.size(); i++) {
                if(first.get(i).id!=second.get(i).id) {
                    ret = false;
                    break;
                }
            }

            return ret;
         }
    }
    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getCharacter(), getCallbackRunner(), getClickListener());
    }
}
