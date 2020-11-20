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
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.entitiy.models.logs.Logger;
import com.example.mviframework.Runner;

import java.util.List;

public class BindingUtils {
    @BindingAdapter(value = {"url", "callbackListener"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Runner callbackListener) {
        new Logger().d("VartikaHilt", "load image: " + url);
        if(url==null || url== "") return;
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
    public static  void loadComicsDataSource(RecyclerView rvComics, List<ProcessedMarvelComic> comicsList) {
       // Log.d("Vartika3", "loadComicsDataSource: "+comicsList);
        RecyclerView.Adapter adapter = rvComics.getAdapter();
        if(adapter==null) {
            ComicsListAdapter comicsListAdapter = new ComicsListAdapter();
            rvComics.setAdapter(comicsListAdapter);
            comicsListAdapter.submitList(comicsList);
        } else {
            ((ComicsListAdapter)adapter).submitList(comicsList);
        }
    }

    @BindingAdapter("seriesDatasource")
    public static  void loadSeriesDataSource(RecyclerView rvSeries, List<ProcessedMarvelSeries> seriesList) {
        RecyclerView.Adapter adapter = rvSeries.getAdapter();
        if(adapter==null) {
            SeriesListAdapter seriesListAdapter = new SeriesListAdapter();
            rvSeries.setAdapter(seriesListAdapter);
            seriesListAdapter.submitList(seriesList);
        } else {
            ((SeriesListAdapter)adapter).submitList(seriesList);
        }
    }

    @BindingAdapter("storiesDatasource")
    public static  void loadStoriesDataSource(RecyclerView rvStories, List<ProcessedMarvelStory> storiesList) {
        RecyclerView.Adapter adapter = rvStories.getAdapter();
        if(adapter==null) {
            StoriesListAdapter storiesListAdapter = new StoriesListAdapter();
            rvStories.setAdapter(storiesListAdapter);
            storiesListAdapter.submitList(storiesList);
        } else {
            ((StoriesListAdapter)adapter).submitList(storiesList);
        }
    }

    @BindingAdapter("eventsDatasource")
    public static  void loadEventsDataSource(RecyclerView rvEvents, List<ProcessedMarvelEvent> eventsList) {
        RecyclerView.Adapter adapter = rvEvents.getAdapter();
        if(adapter==null) {
            EventsListAdapter eventsListAdapter = new EventsListAdapter();
            rvEvents.setAdapter(eventsListAdapter);
            eventsListAdapter.submitList(eventsList);
        } else {
            ((EventsListAdapter)adapter).submitList(eventsList);
        }
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean b) {
        if (b) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @BindingAdapter("hideLabelList")
    public static void setLabelListVisibility(View view, List<? extends ProcessedMarvelItemBase> items) {
        if(items==null) view.setVisibility(View.GONE);
        else if(items!=null && items.size()==0) view.setVisibility(View.GONE);
        else view.setVisibility(View.VISIBLE);
    }
}
