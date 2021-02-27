package com.perpy.marvelapp;




import com.perpy.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.perpy.controllers.characterslist.CharactersListNetworkInterface;
import com.perpy.controllers.characterslist.inmemoryrepository.CharactersListRepository;
import com.perpy.controllers.characterslist.inmemoryrepository.CharactersListRepositoryImpl;
import com.perpy.controllers.comicdetail.ComicDetailNetworkInterface;
import com.perpy.controllers.eventdetail.EventDetailNetworkInterface;
import com.perpy.controllers.seriesdetail.SeriesDetailNetworkInterface;
import com.perpy.controllers.storydetail.StoryDetailNetworkInterface;
import com.perpy.networkcontroller.CharacterDetailNetworkInterfaceImpl;
import com.perpy.networkcontroller.CharactersListNetworkInterfaceImpl;
import com.perpy.networkcontroller.ComicDetailNetworkInterfaceImpl;
import com.perpy.networkcontroller.EventDetailNetworkImpl;
import com.perpy.networkcontroller.SeriesDetailNetworkInterfaceImpl;
import com.perpy.networkcontroller.StoryDetailNetworkImpl;

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

    @Binds
    public abstract ComicDetailNetworkInterface createComicDetailNetworkInterface (
            ComicDetailNetworkInterfaceImpl comicDetailNetworkImpl
    );

    @Binds
    public abstract SeriesDetailNetworkInterface createSeriesDetailNetworkInterface(
            SeriesDetailNetworkInterfaceImpl seriesDetailNetworkImpl
    );

    @Binds
    public abstract StoryDetailNetworkInterface createStoryDetailNetworkInterface(
            StoryDetailNetworkImpl storyDetailNetworkImpl
    );

    @Binds
    public abstract EventDetailNetworkInterface createEventDetailNetworkInterface(
        EventDetailNetworkImpl eventDetailNetworkImpl
    );

    @Binds
    public abstract CharactersListRepository createCharactersListRepository(
            CharactersListRepositoryImpl retrofitImpl
    );
}
