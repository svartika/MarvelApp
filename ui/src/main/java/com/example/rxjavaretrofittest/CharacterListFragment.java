package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.characterslist.CharactersListViewModel;
import com.example.controllers.characterslist.Effect;
import com.example.controllers.characterslist.State;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharacterListBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterListFragment extends Fragment {
    @Inject
    CharactersListViewModel viewModel;
    FragmentCharacterListBinding binding;
    RecyclerView rvMarvelCharacters;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false);
        rvMarvelCharacters = binding.getRoot().findViewById(R.id.rvMarvelCharacters);
        setUpRecyclerView();
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
    void setUpRecyclerView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width / getResources().getDimensionPixelSize(R.dimen.character_image_width);
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(getContext(), span));
    }

    void consume(Effect effect) {

    }

    void render(State state) {
        binding.setState(state);
        binding.executePendingBindings();
    }

}
