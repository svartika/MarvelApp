package com.example.rxjavaretrofittest;




import com.example.controllers.retrofit.CharactersListController;
import com.example.networkcontroller.CharactersListControllerImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class InjectionModule {
    @Binds
    public abstract CharactersListController createRetrofit(
            CharactersListControllerImpl retrofitImpl
    );


}
