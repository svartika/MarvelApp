package com.perpy.marvelapp;

import android.app.Application;

import javax.inject.Inject;

import dagger.hilt.android.HiltAndroidApp;

@HiltAndroidApp
public class MarvelApplication extends Application {


    @Inject
    FlipperSetter flipperSetter;

    @Override
    public void onCreate() {
        super.onCreate();


        flipperSetter.setup();

    }
}
