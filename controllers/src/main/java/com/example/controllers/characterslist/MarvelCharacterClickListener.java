package com.example.controllers.characterslist;

import android.view.View;

public interface MarvelCharacterClickListener<T> {
    public void invoke(View view, T character);
 }
