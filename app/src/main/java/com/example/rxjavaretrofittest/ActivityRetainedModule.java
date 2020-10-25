package com.example.rxjavaretrofittest;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class ActivityRetainedModule {
    @ActivityRetainedScoped
    @Binds
    public abstract AbsCharactersListPageController createCharactersListPageController(
            CharactersListPageController charactersListPageController
    );
}
