package com.perpy.marvelapp;

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
import com.perpy.controllers.storydetail.Effect;
import com.perpy.controllers.storydetail.State;
import com.perpy.controllers.storydetail.StoryDetailViewModel;
import com.perpy.entitiy.models.logs.Logger;
import com.perpy.ui.R;
import com.perpy.ui.databinding.FragmentStoryDetailBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class StoryDetailFragment extends Fragment {

    @Inject
    StoryDetailViewModel viewModel;

    int storyId;
    String storyName;
    ImageView imageView;
    RecyclerView rvCharacters, rvComics, rvSeries, rvEvents;

    @Inject
    Logger logger;

    FragmentStoryDetailBinding binding;
    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_story_detail, container, false);
        ProcessedMarvelStory item = (ProcessedMarvelStory) StoryDetailFragmentArgs.fromBundle(getArguments()).getItem();
        storyId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        imageView = binding.getRoot().findViewById(R.id.image);
        rvCharacters = binding.getRoot().findViewById(R.id.rvCharacters);
        rvComics = binding.getRoot().findViewById(R.id.rvComics);
        rvSeries = binding.getRoot().findViewById(R.id.rvSeries);
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
        imageView.setTransitionName(transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Image));
    }

    void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvCharacters.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(), 1);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvComics.setLayoutManager(layoutManager2);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(), 1);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        rvSeries.setLayoutManager(layoutManager3);

        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(), 1);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        rvEvents.setLayoutManager(layoutManager4);
    }

    void render(State state) {
        storyName = state.getStory().title;
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if (effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        } else if (effect instanceof Effect.CardClickedEffect) {
            ProcessedMarvelItemBase item = (ProcessedMarvelItemBase) ((Effect.CardClickedEffect) effect).item;
            Effect.CardClickedEffect clickCardEffect = (Effect.CardClickedEffect) effect;
            if (item instanceof ProcessedMarvelCharacter) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.StoryDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.mCharacterImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(StoryDetailFragmentDirections.actionStoryDetailFragmentToCharacterDetailFragment(item, ((ProcessedMarvelCharacter) item).name), extras);
                }
            } else if (item instanceof ProcessedMarvelComic) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.StoryDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.comicImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(StoryDetailFragmentDirections.actionStoryDetailFragmentToComicDetailFragment(item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelSeries) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.StoryDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.seriesImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(StoryDetailFragmentDirections.actionStoryDetailFragmentToSeriesDetailFragment(item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelEvent) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.StoryDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.eventImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(StoryDetailFragmentDirections.actionStoryDetailFragmentToEventDetailFragment(item, item.title), extras);
                }
            }
        }
    }
}