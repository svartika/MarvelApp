package com.example.rxjavaretrofittest;

import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.BaseRequestOptions;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.entitiy.models.logs.Logger;
import com.example.mviframework.Runner;
import com.example.rxjavaretrofittest.utils.bitmap.BlurTransformation;

import java.util.List;

public class BindingUtils {
    @BindingAdapter(value = {"url", "callbackListener", "blur"}, requireAll = false)
    public static void loadImage(ImageView imageView, String url, Runner callbackListener, boolean blur) {
        new Logger().d("VartikaHilt", "load image: " + url);
        if (url == null || url == "") {
            if (callbackListener != null) callbackListener.run();
            return;
        }
        RequestBuilder rb = Glide.with(imageView.getContext()).asBitmap()
                .load(url)

                .addListener(new RequestListener<Bitmap>() {
                    @Override
                    public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Bitmap> target, boolean isFirstResource) {
                        if (callbackListener != null) callbackListener.run();
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, Object model, Target<Bitmap> target, DataSource dataSource, boolean isFirstResource) {
                        if (callbackListener != null) callbackListener.run();
                        return false;
                    }


                });
        //.apply(new RequestOptions().circleCrop())
        //.thumbnail(0.2f)
        //.override(15, 15)
        if(blur) {
            BaseRequestOptions rb2 = rb.transform(blur ? new BlurTransformation(imageView.getContext()) : null);
            ((RequestBuilder<Object>) rb2).into(imageView);
        } else {
            rb.into(imageView);
        }

    }

    @BindingAdapter(value = {"onClick", "item"}, requireAll = true)
    public static void onCharacterClicked(View view, CardClickListener clickedListener, Object item) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                clickedListener.invoke(view, item);
            }
        });
    }

    @BindingAdapter(value = {"datasource", "characterClickHandler"}, requireAll = true)
    public static void loadDataSource(RecyclerView rvCharacters, List<ProcessedMarvelCharacter> charactersList, CardClickListener clickListener) {
        RecyclerView.Adapter adapter = rvCharacters.getAdapter();
        CharacterListAdapterLanding characterListAdapterLanding = null;
        if (adapter instanceof CharacterListAdapterLanding) {
            characterListAdapterLanding = (CharacterListAdapterLanding) adapter;
            characterListAdapterLanding.marvelCharacterClickedListener = clickListener;
        } else {
            characterListAdapterLanding = new CharacterListAdapterLanding(clickListener);
            rvCharacters.setAdapter(characterListAdapterLanding);
        }
        characterListAdapterLanding.submitList(charactersList);
    }

    @BindingAdapter(value = {"charactersDatasource", "cardClickHandler"}, requireAll = true)
    public static void loadCharactersDataSource(RecyclerView rvCharacters, List<ProcessedMarvelCharacter> charactersList, CardClickListener clickListener) {
        // Log.d("Vartika3", "loadComicsDataSource: "+comicsList);
        RecyclerView.Adapter adapter = rvCharacters.getAdapter();
        if (adapter == null) {
            CharacterDetailAdapter charactersDetailAdapter = new CharacterDetailAdapter(clickListener);
            rvCharacters.setAdapter(charactersDetailAdapter);
            charactersDetailAdapter.submitList(charactersList);
        } else {
            ((CharacterDetailAdapter) adapter).marvelCharacterClickedListener = clickListener;
            ((CharacterDetailAdapter) adapter).submitList(charactersList);
        }
    }
    @BindingAdapter(value = {"comicsDatasource", "cardClickHandler"}, requireAll = true)
    public static void loadComicsDataSource(RecyclerView rvComics, List<ProcessedMarvelComic> comicsList, CardClickListener clickListener) {
        // Log.d("Vartika3", "loadComicsDataSource: "+comicsList);
        RecyclerView.Adapter adapter = rvComics.getAdapter();
        if (adapter == null) {
            ComicsDetailAdapter comicsDetailAdapter = new ComicsDetailAdapter(clickListener);
            rvComics.setAdapter(comicsDetailAdapter);
            comicsDetailAdapter.submitList(comicsList);
        } else {
            ((ComicsDetailAdapter) adapter).clickListener = clickListener;
            ((ComicsDetailAdapter) adapter).submitList(comicsList);
        }
    }

    @BindingAdapter(value = {"seriesDatasource", "cardClickHandler"}, requireAll = true)
    public static void loadSeriesDataSource(RecyclerView rvSeries, List<ProcessedMarvelSeries> seriesList, CardClickListener clickListener) {
        RecyclerView.Adapter adapter = rvSeries.getAdapter();
        if (adapter == null) {
            SeriesDetailAdapter seriesDetailAdapter = new SeriesDetailAdapter(clickListener);
            rvSeries.setAdapter(seriesDetailAdapter);
            seriesDetailAdapter.submitList(seriesList);
        } else {
            ((SeriesDetailAdapter) adapter).clickListener = clickListener;
            ((SeriesDetailAdapter) adapter).submitList(seriesList);
        }
    }

    @BindingAdapter(value = {"storiesDatasource", "cardClickHandler"}, requireAll = true)
    public static void loadStoriesDataSource(RecyclerView rvStories, List<ProcessedMarvelStory> storiesList, CardClickListener clickListener) {
        RecyclerView.Adapter adapter = rvStories.getAdapter();
        if (adapter == null) {
            StoriesDetailAdapter storiesDetailAdapter = new StoriesDetailAdapter(clickListener);
            rvStories.setAdapter(storiesDetailAdapter);
            storiesDetailAdapter.submitList(storiesList);
        } else {
            ((StoriesDetailAdapter) adapter).clickListener = clickListener;
            ((StoriesDetailAdapter) adapter).submitList(storiesList);
        }
    }

    @BindingAdapter(value = {"eventsDatasource", "cardClickHandler"}, requireAll = true)
    public static void loadEventsDataSource(RecyclerView rvEvents, List<ProcessedMarvelEvent> eventsList, CardClickListener clickListener) {
        RecyclerView.Adapter adapter = rvEvents.getAdapter();
        if (adapter == null) {
            EventsDetailAdapter eventsDetailAdapter = new EventsDetailAdapter(clickListener);
            rvEvents.setAdapter(eventsDetailAdapter);
            eventsDetailAdapter.submitList(eventsList);
        } else {
            ((EventsDetailAdapter) adapter).clickListener = clickListener;
            ((EventsDetailAdapter) adapter).submitList(eventsList);
        }
    }

    @BindingAdapter("visibility")
    public static void setVisibility(View view, boolean b) {
        if (b) view.setVisibility(View.VISIBLE);
        else view.setVisibility(View.GONE);
    }

    @BindingAdapter("hideLabelList")
    public static void setLabelListVisibility(View view, List<? extends ProcessedMarvelItemBase> items) {
        if (items == null) view.setVisibility(View.GONE);
        else if (items != null && items.size() == 0) view.setVisibility(View.GONE);
        else view.setVisibility(View.VISIBLE);
    }
}
