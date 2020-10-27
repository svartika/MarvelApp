package com.example.controllers.characterslist;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.AbstractSavedStateViewModelFactory;
import androidx.lifecycle.SavedStateHandle;
import androidx.lifecycle.ViewModel;
import androidx.savedstate.SavedStateRegistryOwner;

import com.example.controllers.retrofit.ProcessedMarvelCharacter;

import java.util.List;

public class CharactersListViewModelFactory extends AbstractSavedStateViewModelFactory {

    /**
     * Constructs this factory.
     *
     * @param owner       {@link SavedStateRegistryOwner} that will provide restored state for created
     *                    {@link ViewModel ViewModels}
     * @param defaultArgs values from this {@code Bundle} will be used as defaults by
     *                    {@link SavedStateHandle} passed in {@link ViewModel ViewModels}
     *                    if there is no previously saved state
     */
    public CharactersListViewModelFactory(@NonNull SavedStateRegistryOwner owner, @Nullable Bundle defaultArgs) {
        super(owner, defaultArgs);
    }

    @NonNull
    @Override
    protected <T extends ViewModel> T create(@NonNull String key, @NonNull Class<T> modelClass, @NonNull SavedStateHandle handle) {
        return (T) new CharactersListViewModelImpl(new CharactersListModelDelegate());
    }
}
