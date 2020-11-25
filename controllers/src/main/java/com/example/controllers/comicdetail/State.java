package com.example.controllers.comicdetail;

import android.util.Log;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.controllers.commons.Utils;
import com.example.mviframework.Runner;

import java.util.List;
import java.util.Objects;

public class State {
    boolean loading;
    boolean error;
    ProcessedMarvelComic comic;
    List<ProcessedMarvelCharacter> characters;
    List<ProcessedMarvelSeries> series;
    List<ProcessedMarvelStory> stories;
    List<ProcessedMarvelEvent> events;
    Runner callbackRunner;
    public CardClickListener clickListener;

    State(ProcessedMarvelComic comic, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error, Runner callbackRunner, CardClickListener clickListener) {
        this.comic = comic;
        this.characters = characters;
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
                ", comic=" + comic +
                ", characters=" + characters +
                ", series=" + series +
                ", stories=" + stories +
                ", events=" + events +
                ", callbackRunner=" + callbackRunner +
                ", clickListener=" + clickListener +
                '}';
    }

    public ProcessedMarvelComic getComic() {
        return comic;
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

    public List<ProcessedMarvelCharacter> getCharacters() {
        return characters;
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
                getComic().equals(state.getComic()) &&
                getCallbackRunner()==state.getCallbackRunner() &&
                Utils.compareLists(characters, state.characters) &&
                Utils.compareLists(series, state.series) &&
                Utils.compareLists(stories, state.stories) &&
                Utils.compareLists(events, state.events) &&
                getClickListener()==state.getClickListener();
        Log.d("Vartika", "Comic Detail state comparison: " + ret);
        return ret;
    }


    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getComic(), getCharacters(), getSeries(), getStories(), getEvents(), getCallbackRunner(), getClickListener());
    }
}
