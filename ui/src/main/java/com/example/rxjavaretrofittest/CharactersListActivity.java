package com.example.rxjavaretrofittest;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.DisplayMetrics;
import android.util.Log;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
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
    EditText searchView;


    ActivityMainBinding binding;
    @Inject
    Logger logger;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        setUpRecyclerView();
        controller.stateLiveData().observe(this, state -> {
            setState(state);
        });
        controller.effectLiveData().observe(this, effect -> {
            setEffect(effect);
        });
        //controller.loadCharacters();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(x -> {
            Intent intent = new Intent(this, CharacterDetailsActivity.class);
            startActivity(intent);
        });
        setUpSearchView();

    }

    void setUpSearchView() {
        searchView = findViewById(R.id.searchView);
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
        rvMarvelCharacters = findViewById(R.id.rvMarvelCharacters);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width / getResources().getDimensionPixelSize(R.dimen.character_image_width);
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

    private void setEffect(AbsCharactersListPageController.Effect effect) {
        switch (effect.action) {
            case ACTION_CLICK_MARVEL_CHARACTER_ON_LIST:
                ProcessedMarvelCharacter marvelCharacter = (ProcessedMarvelCharacter) effect.item;
                Intent intent = new Intent(this, CharacterDetailsActivity.class);
                intent.putExtra("MARVEL_CHARACTER_ID", marvelCharacter.id);
                startActivity(intent);
                break;
        }
    }
}