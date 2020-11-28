package com.example.controllers.storydetail;

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
    ProcessedMarvelStory story;
    List<ProcessedMarvelCharacter> characters;
    List<ProcessedMarvelComic> comics;
    List<ProcessedMarvelSeries> series;
    List<ProcessedMarvelEvent> events;
    Runner runner;
    CardClickListener clickListener;

    public boolean isLoading() { return loading; }
    public boolean isError() { return error; }
    public ProcessedMarvelStory getStory() { return story; }
    public List<ProcessedMarvelCharacter> getCharacters() {  return characters; }
    public List<ProcessedMarvelComic> getComics() { return comics; }
    public List<ProcessedMarvelSeries> getSeries() { return series; }
    public List<ProcessedMarvelEvent> getEvents() { return events; }
    public Runner getRunner() { return runner;  }
    public CardClickListener getClickListener() { return clickListener; }

    public State(ProcessedMarvelStory story, List<ProcessedMarvelCharacter> characters, List<ProcessedMarvelComic> comics, List<ProcessedMarvelSeries> series, List<ProcessedMarvelEvent> events, boolean loading, boolean error, Runner runner, CardClickListener clickListener) {
        this.loading = loading;
        this.error = error;
        this.story = story;
        this.characters = characters;
        this.comics = comics;
        this.series = series;
        this.events = events;
        this.runner = runner;
        this.clickListener = clickListener;
    }

    @Override
    public String toString() {
        return "State{" +
                "loading=" + loading +
                ", error=" + error +
                ", story=" + story +
                ", characters=" + characters +
                ", comics=" + comics +
                ", series=" + series +
                ", events=" + events +
                ", runner=" + runner +
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
                Objects.equals(getStory(), state.getStory()) &&
                Utils.compareLists(getCharacters(), state.getCharacters()) &&
                Utils.compareLists(getComics(), state.getComics()) &&
                Utils.compareLists(getSeries(), state.getSeries()) &&
                Utils.compareLists(getEvents(), state.getEvents()) &&
                getRunner() == state.getRunner() &&
                getClickListener() == state.getClickListener();
    }

    @Override
    public int hashCode() {
        return Objects.hash(isLoading(), isError(), getStory(), getCharacters(), getComics(), getSeries(), getEvents(), getRunner(), getClickListener());
    }
}
