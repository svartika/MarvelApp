package com.example.rxjavaretrofittest;

import android.app.Activity;

import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStoreOwner;

import com.example.controllers.characterslist.CharactersListModelDelegate;
import com.example.controllers.characterslist.CharactersListViewModel;
import com.example.controllers.characterslist.CharactersListViewModelImpl;
import com.example.controllers.characterslist.CharactersListViewModelFactory;
import com.example.controllers.retrofit.CharactersListNetworkInterface;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ActivityComponent;
import dagger.hilt.android.scopes.ActivityScoped;

@Module
@InstallIn(ActivityComponent.class)
public class ActivityModule {
    @ActivityScoped
    @Provides
    CharactersListViewModel getCharactersListViewModel(Activity activity, CharactersListNetworkInterface charactersListNetworkInterface) {
        ViewModelProvider.Factory factory = new CharactersListViewModelFactory(new CharactersListModelDelegate(charactersListNetworkInterface));
        return new ViewModelProvider((ViewModelStoreOwner) activity, factory).get(CharactersListViewModelImpl.class);
    }
}
