package com.example.controllers.characterslist;

import android.view.View;

import com.example.controllers.retrofit.ProcessedMarvelCharacter;

public interface MarvelCharacterClickListener<T> {
    public void invoke(View view, T character);
 }
