package com.example.controllers.seriesdetail;

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
    ProcessedMarvelSeries series;
    List<ProcessedMarvelCharacter> characters;
    List<ProcessedMarvelComic> comics;
    List<ProcessedMarvelStory> stories;
    List<ProcessedMarvelEvent> events;
    Runner callbackRunner;
    CardClickListener clickListener;

    public State(ProcessedMarvelSeries series, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelStory> stories, List<ProcessedMarvelEvent> events, boolean loading, boolean error, Runner callbackRunner, CardClickListener clickListener) {
        this.loading = loading;
        this.error = error;
        this.series = series;
        this.characters = characters;
        this.comics = comics;
        this.stories = stories;
        this.events = events;
        this.callbackRunner = callbackRunner;
        this.clickListener = clickListener;
    }

    public boolean isLoading() { return loading; }
    public boolean isError() { return error; }
    public ProcessedMarvelSeries getSeries() { return series; }
    public List<ProcessedMarvelCharacter> getCharacters() { return characters; }
    public List<ProcessedMarvelComic> getComics() { return comics; }
    public List<ProcessedMarvelStory> getStories() { return stories; }
    public List<ProcessedMarvelEvent> getEvents() { return events; }
    public Runner getCallbackRunner() { return callbackRunner; }
    public CardClickListener getClickListener() { return clickListener; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return isLoading() == state.isLoading() &&
                isError() == state.isError() &&
                getSeries().equals(state.getSeries()) &&
                Utils.compareLists(getCharacters(), state.getCharacters()) &&
                Utils.compareLists(getComics(), state.getComics()) &&
                Utils.compareLists(getStories(), state.getStories()) &&
                Utils.compareLists(getEvents(), state.getEvents()) &&
                getCallbackRunner()==state.getCallbackRunner()&&
                getClickListener()==state.getClickListener();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getSeries(), getCharacters(), getComics(), getStories(), getEvents(), getCallbackRunner(), getClickListener());
    }

    @Override
    public String toString() {
        return "State{" +
                "loading=" + loading +
                ", error=" + error +
                ", series=" + series +
                ", characters=" + characters +
                ", comics=" + comics +
                ", stories=" + stories +
                ", events=" + events +
                ", callbackRunner=" + callbackRunner +
                ", clickListener=" + clickListener +
                '}';
    }
}
