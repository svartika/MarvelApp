package com.example.rxjavaretrofittest;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;

import com.example.controllers.retrofit.CharactersListNetworkInterface;
import com.example.entitiy.models.MarvelCharacter;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

import com.example.ui.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

@AndroidEntryPoint
public class CharactersListActivity extends AppCompatActivity {


    @Inject
    CharactersListNetworkInterface charactersListNetworkInterface;// = new RetrofitControllerImpl();
    //RecyclerView rvChanges;
    RecyclerView rvMarvelCharacters;
    //ChangesAdapter changesAdapter;
    @Inject
    MarvelCharacterListAdapter marvelCharacterListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setUpRecyclerView();
        //loadChanges();
        loadCharacters();
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
        int width = displayMetrics.densityDpi;
        int span = (int) width/120;
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(this, span));
        //   Log.d("VartikaHilt", marvelCharacterAdapter.toString());
        //changesAdapter = new ChangesAdapter();
        //marvelCharacterAdapter = new MarvelCharacterAdapter();
        rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);
    }

    void displayMarvelCharacters(List<MarvelCharacter> marvelCharacters) {
        marvelCharacterListAdapter.setMarvelCharacters(marvelCharacters);
        marvelCharacterListAdapter.notifyDataSetChanged();
    }

    /* void loadChanges() {
         Observable<List<Change>> changeList = RetrofitController.loadChanges();
         *//*Disposable disposable =*//* changeList
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new DisposableObserver<List<Change>>() {
                    @Override
                    public void onNext(List<Change> changes) {
                        displayChanges(changes);
                        dispose();
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });

    }*/
    void loadCharacters() {
        Log.d("VartikaHilt", "retrofitController object hash -> " + charactersListNetworkInterface.hashCode());
        Observable<List<MarvelCharacter>> marvelCharacters = charactersListNetworkInterface.loadMarvelCharacters();
        Disposable disposable = marvelCharacters
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(marvelCharactersList -> {
                            Log.d("VartikaHilt", "Marvel Characters: " + marvelCharactersList.size());
                            displayMarvelCharacters(marvelCharactersList);
                        },
                        err -> Log.d("VartikaHilt", err.getLocalizedMessage()),
                        () -> Log.d("VartikaHilt", "OnCompleted"));

                                /*new Consumer<List<MarvelCharacter>>() {
                            @Override
                            public void accept(List<MarvelCharacter> marvelCharacters) throws Exception {
                                Log.d("VartikaRx", "Marvel Characters: " + marvelCharacters.size());
                                displayMarvelCharacters(marvelCharacters);
                            }


                        });*/
    }
}