package com.example.controllers.eventdetail;

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
    ProcessedMarvelEvent event;
    List<ProcessedMarvelCharacter> characters;
    List<ProcessedMarvelComic> comics;
    List<ProcessedMarvelSeries> series;
    List<ProcessedMarvelStory> stories;
    Runner callbackRunner;
    CardClickListener clickListener;

    State(ProcessedMarvelEvent event, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelStory> stories, boolean loading, boolean error, Runner runner, CardClickListener clickListener ) {
        this.event = event;
        this.characters = characters;
        this.comics = comics;
        this.series = series;
        this.stories = stories;
        this.error = error;
        this.loading = loading;
        this.callbackRunner = runner;
        this.clickListener = clickListener;
    }

    public ProcessedMarvelEvent getEvent() { return event; }
    public List<ProcessedMarvelCharacter> getCharacters() { return characters;}
    public List<ProcessedMarvelComic> getComics() {  return comics; }
    public List<ProcessedMarvelSeries> getSeries() { return series; }
    public List<ProcessedMarvelStory> getStories() { return stories; }
    public Runner getCallbackRunner() { return callbackRunner; }
    public CardClickListener getClickListener() {  return clickListener;  }
    public boolean isError() { return error; }
    public boolean isLoading() { return loading; }

    @Override
    public String toString() {
        return "State{" +
                "loading=" + loading +
                ", error=" + error +
                ", event=" + event +
                ", characters=" + characters +
                ", comics=" + comics +
                ", series=" + series +
                ", stories=" + stories +
                ", callbackRunner=" + callbackRunner +
                ", clickListener=" + clickListener +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof State)) return false;
        State state = (State) o;
        return isLoading() == state.isLoading() &&
                isError() == state.isError() &&
                getEvent().equals(state.getEvent()) &&
                Utils.compareLists(getCharacters(),state.getCharacters()) &&
                Utils.compareLists(getComics(),state.getComics()) &&
                Utils.compareLists(getSeries(),state.getSeries()) &&
                Utils.compareLists(getStories(),state.getStories()) &&
                getCallbackRunner()==state.getCallbackRunner() &&
                getClickListener()==state.getClickListener();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getEvent(), getCharacters(), getComics(), getSeries(), getStories(), getCallbackRunner(), getClickListener());
    }
}
