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
import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.ICallbackListerner;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
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

    @BindingAdapter(value = {"datasource", "marvelCharacterClickHandler"}, requireAll = true)
    public static void loadDataSourceOLD(RecyclerView rvMarvelCharacters, List<ProcessedMarvelCharacter>marvelCharactersList, MarvelCharacterClickListener marvelCharacterClickedListener) {

        //rvMarvelCharacters.setLayoutManager(new GridLayoutManager(this, span));
        new Logger().d("VartikaHilt", "BindingUtils.loadDataSource");
        //changesAdapter = new ChangesAdapter();
        RecyclerView.Adapter adapter = rvMarvelCharacters.getAdapter();
        MarvelCharacterListAdapter marvelCharacterListAdapter = null;
        if(adapter instanceof MarvelCharacterListAdapter) {
            marvelCharacterListAdapter = (MarvelCharacterListAdapter) adapter;
            marvelCharacterListAdapter.marvelCharacterClickedListener = marvelCharacterClickedListener;
        } else {
            marvelCharacterListAdapter = new MarvelCharacterListAdapter(marvelCharacterClickedListener);
            rvMarvelCharacters.setAdapter(marvelCharacterListAdapter);
        }

       /* marvelCharacterListAdapter.setMarvelCharacters(marvelCharactersList);
        marvelCharacterListAdapter.notifyDataSetChanged();*/
       marvelCharacterListAdapter.submitList(marvelCharactersList);
    }
    @BindingAdapter(value = {"onClick", "item"}, requireAll = true)
    public static void onCharacterClicked(View view, MarvelCharacterClickListener clickedListener, Object item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.invoke(view, item);
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

}
