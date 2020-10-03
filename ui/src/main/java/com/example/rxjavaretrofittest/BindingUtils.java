package com.example.rxjavaretrofittest;

import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.entitiy.models.logs.Logger;

import java.util.List;

public class BindingUtils {

    @BindingAdapter("url")
    public static void loadImage(ImageView imageView, String url) {
        new Logger().d("VartikaHilt", "load image: " + url);
        Glide.with(imageView.getContext())
                .load(url)
                //.apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

    @BindingAdapter("datasource")
    public static void loadDataSource(RecyclerView rvMarvelCharacters, List<ProcessedMarvelCharacter>marvelCharactersList) {

        //rvMarvelCharacters.setLayoutManager(new GridLayoutManager(this, span));
        new Logger().d("VartikaHilt", "BindingUtils.loadDataSource");
        //changesAdapter = new ChangesAdapter();
        MarvelCharacterListAdapter marvelCharacterListAdapter = new MarvelCharacterListAdapter();
        rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);
       /* marvelCharacterListAdapter.setMarvelCharacters(marvelCharactersList);
        marvelCharacterListAdapter.notifyDataSetChanged();*/
       marvelCharacterListAdapter.submitList(marvelCharactersList);
    }
}
