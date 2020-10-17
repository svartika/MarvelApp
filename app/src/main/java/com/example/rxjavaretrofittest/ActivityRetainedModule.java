package com.example.rxjavaretrofittest;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.WithFragmentBindings;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.components.ActivityRetainedComponent;
import dagger.hilt.android.scopes.ActivityRetainedScoped;
import dagger.hilt.android.scopes.ActivityScoped;
import dagger.hilt.android.scopes.FragmentScoped;
import dagger.hilt.android.scopes.ViewScoped;

@Module
@InstallIn(ActivityRetainedComponent.class)
public abstract class ActivityRetainedModule {
    @ActivityRetainedScoped
    @Binds
    public abstract AbsCharactersListPageController createCharactersListPageController(
            CharactersListPageController charactersListPageController
    );
}
