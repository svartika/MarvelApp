package com.example.entitiy.models.logs;

import android.util.Log;

import javax.inject.Inject;

public class Logger {
    @Inject
    public Logger() {

    }

    public void d(String tag, String message) {
        Log.d(tag, message);
    }
}
