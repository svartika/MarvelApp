package com.example.mviframework;

public interface Reducer<InnerState, Effect> {
    Change<InnerState, Effect> reduce(InnerState innerState);
}
