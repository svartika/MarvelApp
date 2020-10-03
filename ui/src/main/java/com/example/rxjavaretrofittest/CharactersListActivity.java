package com.example.rxjavaretrofittest;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.ActivityMainBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharactersListActivity extends AppCompatActivity {

    @Inject
    AbsCharactersListPageController controller;
    RecyclerView rvMarvelCharacters;
    @Inject
    MarvelCharacterListAdapter marvelCharacterListAdapter;
    ActivityMainBinding binding;
    @Inject
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpRecyclerView();
        controller.getCharactersLiveData().observe(this, state -> { setState (state); });
        controller.loadCharacters();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(x -> {
            Intent intent = new Intent(this, CharacterDetailsActivity.class);
            startActivity(intent);
        });
    }

    void setUpRecyclerView() {
        rvMarvelCharacters = findViewById(R.id.rvMarvelCharacters);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width/getResources().getDimensionPixelSize(R.dimen.character_image_width);
        //int span = (int) width/120;
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(this, span));
        /*rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);*/
    }

    /*void displayMarvelCharacters(List<ProcessedMarvelCharacter> marvelCharacters) {
        marvelCharacterListAdapter.setMarvelCharacters(marvelCharacters);
        marvelCharacterListAdapter.notifyDataSetChanged();
    }*/


    private void setState(CharactersListPageController.State state) {
        binding.setState(state);
    }

}