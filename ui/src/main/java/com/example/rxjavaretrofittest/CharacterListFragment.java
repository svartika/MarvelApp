package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.controllers.characterslist.CharactersListViewModel;
import com.example.controllers.characterslist.CharactersListViewModelFactory;
import com.example.controllers.characterslist.CharactersListViewModelImpl;
import com.example.controllers.characterslist.Effect;
import com.example.controllers.characterslist.State;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharacterListBinding;

public class CharacterListFragment extends Fragment {
    CharactersListViewModel viewModel;
    FragmentCharacterListBinding binding;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewModel = init();

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false);
        viewModel.getState().observe(getViewLifecycleOwner(), new Observer<State>() {
            @Override
            public void onChanged(State state) {
                render(state);
            }
        });
        viewModel.getEffect().observe(getViewLifecycleOwner(), new Observer<Effect>() {
            @Override
            public void onChanged(Effect effect) {
                consume(effect);
            }
        });
        return binding.getRoot();
    }

    void consume(Effect effect) {

    }

    void render(State state) {
        binding.setState(state);
        binding.executePendingBindings();
    }

    CharactersListViewModel init() {
        CharactersListViewModelFactory viewModelFactory = new CharactersListViewModelFactory(this, this.getArguments());
        ViewModelStore viewModelStore = new ViewModelStore();
        ViewModelProvider viewModelProvider = new ViewModelProvider(viewModelStore, viewModelFactory);
        return viewModelProvider.get(CharactersListViewModelImpl.class);
    }
}
