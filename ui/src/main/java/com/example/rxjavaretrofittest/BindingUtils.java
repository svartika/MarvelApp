package com.example.rxjavaretrofittest;

import android.view.View;
import android.widget.ImageView;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.controllers.retrofit.AbsCharactersListPageController;
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

    @BindingAdapter(value = {"datasource", "marvelCharacterClickHandler"}, requireAll = true)
    public static void loadDataSource(RecyclerView rvMarvelCharacters, List<ProcessedMarvelCharacter>marvelCharactersList, AbsCharactersListPageController.AbsMarvelCharacterClickedListener marvelCharacterClickedListener) {

        //rvMarvelCharacters.setLayoutManager(new GridLayoutManager(this, span));
        new Logger().d("VartikaHilt", "BindingUtils.loadDataSource");
        //changesAdapter = new ChangesAdapter();
        MarvelCharacterListAdapter marvelCharacterListAdapter = new MarvelCharacterListAdapter(marvelCharacterClickedListener);
        rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);
       /* marvelCharacterListAdapter.setMarvelCharacters(marvelCharactersList);
        marvelCharacterListAdapter.notifyDataSetChanged();*/
       marvelCharacterListAdapter.submitList(marvelCharactersList);
    }
    @BindingAdapter(value = {"onClick", "item"}, requireAll = true)
    public static void onCharacterClicked(View view, AbsCharactersListPageController.AbsMarvelCharacterClickedListener clickedListener, Object item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.invoke(item);
            }
        });
    }


}
