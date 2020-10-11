package com.example.rxjavaretrofittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.example.controllers.retrofit.CharacterDetailPageController;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharacterDetailsBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailFragment extends Fragment {
    @Inject
    CharacterDetailPageController controller;

    int characterId;
    TextView name;
    FragmentCharacterDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);

        Intent intent = getActivity().getIntent();
        characterId = intent.getIntExtra("MARVEL_CHARACTER_ID", 0);

        name = binding.getRoot().findViewById(R.id.name);
        controller.getCharacterDetailLiveData().observe(getViewLifecycleOwner(),
                (state -> {
                    setState(state);
                }
                ));
        loadCharacter();

        return binding.getRoot();
    }

    void loadCharacter() {
       // characterId = 1011334;
        controller.loadCharacterDetails(characterId);
    }

    void setState(CharacterDetailPageController.State state) {
        //Log.d("VartikaHilt", "marvelCharacter.name->" + state.character.name);
        binding.setState(state);
    }
}
