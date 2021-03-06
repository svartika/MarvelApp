package com.perpy.marvelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelItemBase;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.controllers.seriesdetail.Effect;
import com.perpy.controllers.seriesdetail.SeriesDetailViewModel;
import com.perpy.controllers.seriesdetail.State;
import com.perpy.entitiy.models.logs.Logger;
import com.perpy.ui.R;
import com.perpy.ui.databinding.FragmentSeriesDetailBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SeriesDetailFragment extends Fragment {

    @Inject
    SeriesDetailViewModel viewModel;

    int seriesId;
    String seriesName;
    ImageView imageView;
    RecyclerView rvCharacters, rvComics, rvStories, rvEvents;
    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Inject
    Logger logger;

    FragmentSeriesDetailBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_series_detail, container, false);
        ProcessedMarvelSeries item = (ProcessedMarvelSeries) SeriesDetailFragmentArgs.fromBundle(getArguments()).getItem();
        seriesId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        imageView = binding.getRoot().findViewById(R.id.image);
        rvCharacters = binding.getRoot().findViewById(R.id.rvCharacters);
        rvComics = binding.getRoot().findViewById(R.id.rvComics);
        rvStories = binding.getRoot().findViewById(R.id.rvStories);
        rvEvents = binding.getRoot().findViewById(R.id.rvEvents);
        setUpRecyclerView();

        viewModel.getState().observe(getViewLifecycleOwner(), new Observer<State>() {
            @Override
            public void onChanged(State state) {
                render(state);
            }
        });

        viewModel.getEffect().observe(getViewLifecycleOwner(), new Observer<Effect>() {
            @Override
            public void onChanged(Effect effect) {
                consume(effect);
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setTransitionName(transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Image));
    }

    void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvCharacters.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),1);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvComics.setLayoutManager(layoutManager2);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(),1);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        rvStories.setLayoutManager(layoutManager3);

        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(),1);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        rvEvents.setLayoutManager(layoutManager4);
    }

    void render(State state) {
        seriesName = state.getSeries().title;
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if(effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        } else if(effect instanceof Effect.CardClickedEffect) {
            ProcessedMarvelItemBase item = (ProcessedMarvelItemBase)((Effect.CardClickedEffect) effect).item;
            Effect.CardClickedEffect clickCardEffect =  (Effect.CardClickedEffect) effect;
            if(item instanceof ProcessedMarvelCharacter) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.SeriesDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.mCharacterImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToCharacterDetailFragment(item, ((ProcessedMarvelCharacter) item).name), extras);
                }
            } else if( item instanceof ProcessedMarvelComic) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.comicImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToComicDetailFragment(item, item.title), extras);
                }
            } else if(item instanceof ProcessedMarvelStory) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.storyImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToStoryDetailFragment(item, item.title), extras);
                }
            } else if(item instanceof ProcessedMarvelEvent) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.eventImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToEventDetailFragment(item, item.title), extras);
                }
            }
        } else if(effect instanceof Effect.OpenUrl) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(((Effect.OpenUrl) effect).url));
            startActivity(intent);
        } else if (effect instanceof Effect.Share) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, ((Effect.Share)effect).url);
            sendIntent.setType("text/plain");

            Intent shareIntent = Intent.createChooser(sendIntent, null);
            startActivity(shareIntent);
        }
    }
}