package com.example.rxjavaretrofittest;

import android.os.Bundle;

import com.example.controllers.retrofit.CharactersListController;
import com.example.entitiy.models.MarvelCharacter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.ui.R;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class CharacterDetails extends AppCompatActivity {

    @Inject
    CharactersListController charactersListController;
    String characterId;
    TextView name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second_screen2);
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
        loadCharacter();
    }

    void loadCharacter() {
        Log.d("VartikaHilt", "retrofitController object hash -> " + charactersListController.hashCode());
        characterId = "1011334";
        Observable<MarvelCharacter> marvelCharacter = charactersListController.loadMarvelCharacter(characterId);
        Disposable disposable = marvelCharacter
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(x -> {
                    Log.d("VartikaHilt", x.toString());
                    displayCharacter(x);
                }, err -> {
                    Log.d("VartikaHilt", err.getLocalizedMessage());
                }, () -> {
                    Log.d("VartikaHilt", "On Completed");
                });
    }

    void displayCharacter(MarvelCharacter marvelCharacter) {
        Log.d("VartikaHilt", "marvelCharacter.name->" + marvelCharacter.name);
        name.setText(marvelCharacter.name);
    }
}