package com.example.rxjavaretrofittest;

import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharacterDetailPageController;
import com.example.controllers.retrofit.ICallbackListerner;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.entitiy.models.logs.Logger;
import com.example.mviframework.Runner;

import java.util.List;

public class BindingUtils {
    @BindingAdapter(value = {"url", "callbackListener"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, ICallbackListerner callbackListener) {
        new Logger().d("VartikaHilt", "load image: " + url);
        Glide.with(imageView.getContext())
                .load(url)
                .addListener(new RequestListener<Drawable>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                        if(callbackListener!=null) callbackListener.callback();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                        if(callbackListener!=null) callbackListener.callback();
                        return false;
                    }
                })
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
                clickedListener.invoke(view, item);
            }
        });
    }

    @BindingAdapter(value = {"clickRunner"})
    public static void clickRunner(View view, Runner runner) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runner.run();
            }
        });
    }

}
