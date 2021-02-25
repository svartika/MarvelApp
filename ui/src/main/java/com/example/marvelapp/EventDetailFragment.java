package com.example.marvelapp;

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

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.controllers.eventdetail.Effect;
import com.example.controllers.eventdetail.EventDetailViewModel;
import com.example.controllers.eventdetail.State;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentEventDetailBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class EventDetailFragment extends Fragment {

    @Inject
    EventDetailViewModel viewModel;

    int eventId;
    String eventName;
    ImageView imageView;
    RecyclerView rvCharacters, rvComics, rvSeries, rvStories;

    @Inject
    Logger logger;

    FragmentEventDetailBinding binding;
    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_event_detail, container, false);
        ProcessedMarvelEvent item = (ProcessedMarvelEvent) EventDetailFragmentArgs.fromBundle(getArguments()).getItem();
        eventId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        imageView = binding.getRoot().findViewById(R.id.image);
        rvCharacters = binding.getRoot().findViewById(R.id.rvComics);
        rvComics = binding.getRoot().findViewById(R.id.rvComics);
        rvSeries = binding.getRoot().findViewById(R.id.rvSeries);
        rvStories = binding.getRoot().findViewById(R.id.rvStories);
        setUpRecyclerView();

        viewModel.getState().observe(getViewLifecycleOwner(), new Observer<State>() {
            @Override
            public void onChanged(com.example.controllers.eventdetail.State state) {
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
        imageView.setTransitionName(transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Image));
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
        rvStories.setLayoutManager(layoutManager4);
    }

    void render(State state) {
        eventName = state.getEvent().title;
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if (effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        } else if (effect instanceof Effect.CardClickedEffect) {
            Effect.CardClickedEffect clickCardEffect = (Effect.CardClickedEffect) effect;
            ProcessedMarvelItemBase item = (ProcessedMarvelItemBase) ((Effect.CardClickedEffect) effect).item;
            if (item instanceof ProcessedMarvelCharacter) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.EventDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.mCharacterImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(EventDetailFragmentDirections.actionEventDetailFragmentToCharacterDetailFragment(item, ((ProcessedMarvelCharacter) item).name), extras);
                }
            } else if (item instanceof ProcessedMarvelComic) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.EventDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.comicImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(EventDetailFragmentDirections.actionEventDetailFragmentToComicDetailFragment(item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelSeries) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.EventDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.seriesImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(EventDetailFragmentDirections.actionEventDetailFragmentToSeriesDetailFragment(item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelStory) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.EventDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.storyImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(EventDetailFragmentDirections.actionEventDetailFragmentToStoryDetailFragment(item, item.title), extras);
                }
            }
        } else if(effect instanceof Effect.OpenUrl) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(((Effect.OpenUrl) effect).url));
            startActivity(intent);
        }
    }
}