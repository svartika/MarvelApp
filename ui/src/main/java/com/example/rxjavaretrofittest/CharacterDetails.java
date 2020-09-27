package com.example.rxjavaretrofittest;

import android.os.Bundle;

import com.example.controllers.retrofit.CharacterDetailPageController;
import com.example.ui.databinding.ActivityCharacterDetailBinding;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.databinding.DataBindingUtil;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ui.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetails extends AppCompatActivity {

    @Inject
    CharacterDetailPageController controller;

    String characterId;
    TextView name;
    ActivityCharacterDetailBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_character_detail);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        name = findViewById(R.id.name);
        controller.getCharacterDetailLiveData().observe(this,
                (state -> {
                    displayCharacter(state);
                }
                ));
        loadCharacter();
    }

    void loadCharacter() {
        characterId = "1011334";
        controller.loadCharacterDetails(characterId);
        /*Disposable disposable = marvelCharacter
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    Log.d("VartikaHilt", x.toString());
                    displayCharacter(x);
                }, err -> {
                    Log.d("VartikaHilt", err.getLocalizedMessage());
                }, () -> {
                    Log.d("VartikaHilt", "On Completed");
                });*/
    }

    void displayCharacter(CharacterDetailPageController.State state) {
        //Log.d("VartikaHilt", "marvelCharacter.name->" + state.character.name);
        binding.setState(state);
    }
}