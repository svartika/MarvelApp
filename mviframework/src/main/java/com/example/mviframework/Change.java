package com.example.mviframework;

import java.util.ArrayList;
import java.util.List;

public class Change<State, Effect> {
    State state;
    List<Effect> effects;
    Change(State state, List<Effect> effects) {
        this.state = state;
        if(effects!=null) {
            this.effects = effects;
        } else {
            this.effects = new ArrayList<>();
        }
    }
}
