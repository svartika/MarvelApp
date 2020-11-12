package com.example.rxjavaretrofittest;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.controllers.characterdetail.CharacterDetailViewModel;
import com.example.controllers.characterdetail.CharacterDetailViewModelDelegate;
import com.example.controllers.characterdetail.CharacterDetailViewModelFactory;
import com.example.controllers.characterdetail.CharacterDetailViewModelImpl;
import com.example.controllers.characterslist.CharactersListModelDelegate;
import com.example.controllers.characterslist.CharactersListViewModel;
import com.example.controllers.characterslist.CharactersListViewModelFactory;
import com.example.controllers.characterslist.CharactersListViewModelImpl;
import com.example.controllers.characterdetail.CharacterDetailNetworkInterface;
import com.example.controllers.characterslist.CharactersListNetworkInterface;
import com.example.controllers.commons.ProcessedMarvelCharacter;

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
    CharactersListViewModel getCharactersListViewModel(Fragment fragment, CharactersListNetworkInterface charactersListNetworkInterface) {
        ViewModelProvider.Factory factory = new CharactersListViewModelFactory(new CharactersListModelDelegate(charactersListNetworkInterface));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(CharactersListViewModelImpl.class);
    }

    @FragmentScoped
    @Provides
    CharacterDetailViewModel getCharacterDetailViewModel(Fragment fragment, CharacterDetailNetworkInterface networkInterface) {
        ProcessedMarvelCharacter item = CharacterDetailFragmentArgs.fromBundle(fragment.getArguments()).getItem();
        ViewModelProvider.Factory factory = new CharacterDetailViewModelFactory(new CharacterDetailViewModelDelegate(networkInterface, item));
        return new ViewModelProvider((ViewModelStoreOwner) fragment, factory).get(CharacterDetailViewModelImpl.class);
    }
}
