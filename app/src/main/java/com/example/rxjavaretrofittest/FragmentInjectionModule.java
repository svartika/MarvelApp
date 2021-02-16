package com.example.rxjavaretrofittest;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.controllers.characterdetail.CharacterDetailViewModel;
import com.example.controllers.characterdetail.CharacterDetailViewModelDelegate;
import com.example.controllers.characterdetail.CharacterDetailViewModelFactory;
import com.example.controllers.characterdetail.CharacterDetailViewModelImpl;
import com.example.controllers.characterslist.CharactersListModelDelegate;
import com.example.controllers.characterslist.CharactersListNetworkInterface;
import com.example.controllers.characterslist.CharactersListViewModel;
import com.example.controllers.characterslist.CharactersListViewModelFactory;
import com.example.controllers.characterslist.CharactersListViewModelImpl;
import com.example.controllers.characterslist.inmemoryrepository.CharactersListRepository;
import com.example.controllers.comicdetail.ComicDetailNetworkInterface;
import com.example.controllers.comicdetail.ComicDetailViewModel;
import com.example.controllers.comicdetail.ComicDetailViewModelDelegate;
import com.example.controllers.comicdetail.ComicDetailViewModelFactory;
import com.example.controllers.comicdetail.ComicDetailViewModelImpl;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.controllers.eventdetail.EventDetailNetworkInterface;
import com.example.controllers.eventdetail.EventDetailViewModel;
import com.example.controllers.eventdetail.EventDetailViewModelDelegate;
import com.example.controllers.eventdetail.EventDetailViewModelFactory;
import com.example.controllers.eventdetail.EventDetailViewModelImpl;
import com.example.controllers.seriesdetail.SeriesDetailNetworkInterface;
import com.example.controllers.seriesdetail.SeriesDetailViewModel;
import com.example.controllers.seriesdetail.SeriesDetailViewModelDelegate;
import com.example.controllers.seriesdetail.SeriesDetailViewModelFactory;
import com.example.controllers.seriesdetail.SeriesDetailViewModelImpl;
import com.example.controllers.storydetail.StoryDetailNetworkInterface;
import com.example.controllers.storydetail.StoryDetailViewModel;
import com.example.controllers.storydetail.StoryDetailViewModelDelegate;
import com.example.controllers.storydetail.StoryDetailViewModelFactory;
import com.example.controllers.storydetail.StoryDetailViewModelImpl;

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
    CharactersListViewModel getCharactersListViewModel(Fragment fragment, CharactersListNetworkInterface charactersListNetworkInterface, CharactersListRepository repository) {
        ViewModelProvider.Factory factory = new CharactersListViewModelFactory(new CharactersListModelDelegate(charactersListNetworkInterface, repository));
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
