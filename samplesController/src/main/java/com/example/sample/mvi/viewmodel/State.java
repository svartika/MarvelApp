package com.example.sample.mvi.viewmodel;

import com.example.mviframework.Runner;

public class State {
    String id;
    String dependency;
    Runner clicktoincrement;
    public State(String id, String dependency, Runner clicktoincrement) {
        this.id = id;
        this.dependency = dependency;
        this.clicktoincrement = clicktoincrement;
    }

    public String getId() {
        return id;
    }

    public String getDependency() {
        return dependency;
    }

    public Runner getClicktoincrement() {
        return clicktoincrement;
    }
}