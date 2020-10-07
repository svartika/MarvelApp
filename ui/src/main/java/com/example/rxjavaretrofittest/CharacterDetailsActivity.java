package com.example.rxjavaretrofittest;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import com.example.controllers.retrofit.CharacterDetailPageController;
import com.example.ui.R;
import com.example.ui.databinding.ActivityCharacterDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailsActivity extends AppCompatActivity {

    @Inject
    CharacterDetailPageController controller;

    int characterId;
    TextView name;
    ActivityCharacterDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Intent intent = getIntent();
        characterId = intent.getIntExtra("MARVEL_CHARACTER_ID", 0);
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        name = findViewById(R.id.name);
        controller.getCharacterDetailLiveData().observe(this,
                (state -> {
                    setState(state);
                }
                ));
        loadCharacter();
    }

    void loadCharacter() {
       // characterId = "1011334";
        controller.loadCharacterDetails(characterId);
    }

    void setState(CharacterDetailPageController.State state) {
        //Log.d("VartikaHilt", "marvelCharacter.name->" + state.character.name);
        binding.setState(state);
    }
}