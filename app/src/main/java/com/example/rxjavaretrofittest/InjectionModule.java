package com.example.rxjavaretrofittest;




import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.controllers.characterslist.CharactersListNetworkInterface;
import com.example.networkcontroller.CharacterDetailNetworkInterfaceImpl;
import com.example.networkcontroller.CharactersListNetworkInterfaceImpl;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ApplicationComponent;

@Module
@InstallIn(ApplicationComponent.class)
public abstract class InjectionModule {
    @Binds
    public abstract CharactersListNetworkInterface createRetrofit(
            CharactersListNetworkInterfaceImpl retrofitImpl
    );

    @Binds
    public abstract CharacterDetailNetworkInterface createDetailPageController (
            CharacterDetailNetworkInterfaceImpl detailPageControllerImpl
    );


}
