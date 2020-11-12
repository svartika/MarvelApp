package com.example.rxjavaretrofittest;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.controllers.characterslist.MarvelCharacterClickListener;
import com.example.controllers.commons.ProcessedCollection;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.entitiy.models.logs.Logger;
import com.example.mviframework.Runner;

import java.util.List;

public class BindingUtils {
    @BindingAdapter(value = {"url", "callbackListener"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Runner callbackListener) {
        new Logger().d("VartikaHilt", "load image: " + url);
        if(url==null) return;
        Glide.with(imageView.getContext())
                .load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(callbackListener!=null) callbackListener.run();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(callbackListener!=null) callbackListener.run();
                        return false;
                    }
                })
                //.apply(new RequestOptions().circleCrop())
                .into(imageView);
    }

    @BindingAdapter(value = {"onClick", "item"}, requireAll = true)
    public static void onCharacterClicked(View view, MarvelCharacterClickListener clickedListener, Object item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.invoke(view,item);
            }
        });
    }

    @BindingAdapter(value = { "datasource","characterClickHandler"}, requireAll = true)
    public static void loadDataSource(RecyclerView rvCharacters, List<ProcessedMarvelCharacter> charactersList, MarvelCharacterClickListener clickListener) {
        RecyclerView.Adapter adapter = rvCharacters.getAdapter();
        MarvelCharacterListAdapter marvelCharacterListAdapter = null;
        if(adapter instanceof MarvelCharacterListAdapter) {
            marvelCharacterListAdapter = (MarvelCharacterListAdapter)adapter;
            marvelCharacterListAdapter.marvelCharacterClickedListener = clickListener;
        } else {
            marvelCharacterListAdapter = new MarvelCharacterListAdapter(clickListener);
            rvCharacters.setAdapter(marvelCharacterListAdapter);
        }
        marvelCharacterListAdapter.submitList(charactersList);
    }
    @BindingAdapter("comicsDatasource")
    public static  void loadComicsDataSource(RecyclerView rvComics, List<ProcessedCollection.ProcessedItem> comicsList) {
        RecyclerView.Adapter adapter = rvComics.getAdapter();
        if(adapter==null) {
            ComicsListAdapter comicsListAdapter = new ComicsListAdapter();
            rvComics.setAdapter(comicsListAdapter);
            comicsListAdapter.submitList(comicsList);
        } else {
            ((ComicsListAdapter)adapter).submitList(comicsList);
        }
    }
}
