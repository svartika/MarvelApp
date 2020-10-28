package com.example.rxjavaretrofittest;

import androidx.fragment.app.Fragment;

import com.example.controllers.characterslist.CharactersListViewModelFactory;
import com.example.controllers.retrofit.CharactersListNetworkInterface;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.FragmentComponent;

@Module
@InstallIn(FragmentComponent.class)
public class FragmentInjectionModule {
    @Provides
    public CharactersListViewModelFactory bindCharactersListViewModelFactory(Fragment fragment, CharactersListNetworkInterface networkInterface) {
        CharactersListViewModelFactory factory = new CharactersListViewModelFactory(fragment, fragment.getArguments());
        factory.setCharactersListNetworkInterface(networkInterface);
        return factory;
    }




}