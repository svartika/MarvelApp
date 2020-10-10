package com.example.rxjavaretrofittest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;
import com.example.controllers.retrofit.Effect;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharactersListBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharactersListFragment extends Fragment {

    @Inject
    AbsCharactersListPageController controller;
    RecyclerView rvMarvelCharacters;
    EditText searchView;

    FragmentCharactersListBinding binding;
    @Inject
    Logger logger;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters_list, container, false);
        rvMarvelCharacters = binding.getRoot().findViewById(R.id.rvMarvelCharacters);
        searchView = binding.getRoot().findViewById(R.id.searchView);
        setUpRecyclerView();

        setUpSearchView();
        controller.stateLiveData().observe(getViewLifecycleOwner(), state -> {
            setState(state);
        });
        controller.effectLiveData().observe(getViewLifecycleOwner(), effect -> {
            setEffect(effect);
        });
        return binding.getRoot();
    }


    public CharactersListFragment() {

    }

    void setUpSearchView() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.searchCharacter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void setUpRecyclerView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width / getResources().getDimensionPixelSize(R.dimen.character_image_width);
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(getContext(), span));
    }

    private void setState(CharactersListPageController.State state) {
        binding.setState(state);
    }

    private void setEffect(Effect effect) {
        if (effect instanceof AbsCharactersListPageController.ClickEffect) {

            ProcessedMarvelCharacter marvelCharacter = (ProcessedMarvelCharacter) ((AbsCharactersListPageController.ClickEffect) effect).item;
            Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
            intent.putExtra("MARVEL_CHARACTER_ID", marvelCharacter.id);
            startActivity(intent);
        }
    }
}