package com.example.rxjavaretrofittest;




import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.controllers.characterslist.CharactersListNetworkInterface;
import com.example.controllers.characterslist.inmemoryrepository.CharactersListRepository;
import com.example.controllers.characterslist.inmemoryrepository.CharactersListRepositoryImpl;
import com.example.controllers.comicdetail.ComicDetailNetworkInterface;
import com.example.controllers.eventdetail.EventDetailNetworkInterface;
import com.example.controllers.seriesdetail.SeriesDetailNetworkInterface;
import com.example.controllers.storydetail.StoryDetailNetworkInterface;
import com.example.networkcontroller.CharacterDetailNetworkInterfaceImpl;
import com.example.networkcontroller.CharactersListNetworkInterfaceImpl;
import com.example.networkcontroller.ComicDetailNetworkInterfaceImpl;
import com.example.networkcontroller.EventDetailNetworkImpl;
import com.example.networkcontroller.SeriesDetailNetworkInterfaceImpl;
import com.example.networkcontroller.StoryDetailNetworkImpl;

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
