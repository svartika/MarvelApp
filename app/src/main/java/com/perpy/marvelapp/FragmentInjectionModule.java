package com.perpy.marvelapp;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.perpy.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.perpy.controllers.characterdetail.CharacterDetailViewModel;
import com.perpy.controllers.characterdetail.CharacterDetailViewModelDelegate;
import com.perpy.controllers.characterdetail.CharacterDetailViewModelFactory;
import com.perpy.controllers.characterdetail.CharacterDetailViewModelImpl;
import com.perpy.controllers.characterslist.CharactersListModelDelegate;
import com.perpy.controllers.characterslist.CharactersListViewModel;
import com.perpy.controllers.characterslist.CharactersListViewModelFactory;
import com.perpy.controllers.characterslist.CharactersListViewModelImpl;
import com.perpy.controllers.characterslist.inmemoryrepository.CharactersListRepository;
import com.perpy.controllers.comicdetail.ComicDetailNetworkInterface;
import com.perpy.controllers.comicdetail.ComicDetailViewModel;
import com.perpy.controllers.comicdetail.ComicDetailViewModelDelegate;
import com.perpy.controllers.comicdetail.ComicDetailViewModelFactory;
import com.perpy.controllers.comicdetail.ComicDetailViewModelImpl;
import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.controllers.eventdetail.EventDetailNetworkInterface;
import com.perpy.controllers.eventdetail.EventDetailViewModel;
import com.perpy.controllers.eventdetail.EventDetailViewModelDelegate;
import com.perpy.controllers.eventdetail.EventDetailViewModelFactory;
import com.perpy.controllers.eventdetail.EventDetailViewModelImpl;
import com.perpy.controllers.seriesdetail.SeriesDetailNetworkInterface;
import com.perpy.controllers.seriesdetail.SeriesDetailViewModel;
import com.perpy.controllers.seriesdetail.SeriesDetailViewModelDelegate;
import com.perpy.controllers.seriesdetail.SeriesDetailViewModelFactory;
import com.perpy.controllers.seriesdetail.SeriesDetailViewModelImpl;
import com.perpy.controllers.storydetail.StoryDetailNetworkInterface;
import com.perpy.controllers.storydetail.StoryDetailViewModel;
import com.perpy.controllers.storydetail.StoryDetailViewModelDelegate;
import com.perpy.controllers.storydetail.StoryDetailViewModelFactory;
import com.perpy.controllers.storydetail.StoryDetailViewModelImpl;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;
import dagger.hilt.android.scopes.FragmentScoped;

@Module
@InstallIn(FragmentComponent.class)
public class FragmentInjectionModule {
    @FragmentScoped
    @Provides
    CharactersListViewModel getCharactersListViewModel(Fragment fragment, CharactersListRepository repository) {
        ViewModelProvider.Factory factory = new CharactersListViewModelFactory(new CharactersListModelDelegate(repository));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(CharactersListViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    CharacterDetailViewModel getCharacterDetailViewModel(Fragment fragment, CharacterDetailNetworkInterface networkInterface) {
        ProcessedMarvelCharacter item = (ProcessedMarvelCharacter) CharacterDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new CharacterDetailViewModelFactory(new CharacterDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(CharacterDetailViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    ComicDetailViewModel getComicDetailViewModel(Fragment fragment, ComicDetailNetworkInterface networkInterface) {
        ProcessedMarvelComic item = (ProcessedMarvelComic)ComicDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new ComicDetailViewModelFactory(new ComicDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(ComicDetailViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    SeriesDetailViewModel getSeriesDetailViewModel(Fragment fragment, SeriesDetailNetworkInterface networkInterface) {
        ProcessedMarvelSeries item = (ProcessedMarvelSeries)SeriesDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new SeriesDetailViewModelFactory(new SeriesDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(SeriesDetailViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    StoryDetailViewModel getStoryDetailViewModel(Fragment fragment, StoryDetailNetworkInterface networkInterface) {
        ProcessedMarvelStory item = (ProcessedMarvelStory) StoryDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new StoryDetailViewModelFactory(new StoryDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner)fragment, factory).get(StoryDetailViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    EventDetailViewModel getEventDetailViewModel(Fragment fragment, EventDetailNetworkInterface networkInterface) {
        ProcessedMarvelEvent item = (ProcessedMarvelEvent) EventDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new EventDetailViewModelFactory(new EventDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner)fragment, factory).get(EventDetailViewModelImpl.class);
    }
}
