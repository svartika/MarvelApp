package com.perpy.marvelapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
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

import com.perpy.controllers.characterdetail.CharacterDetailViewModel;
import com.perpy.controllers.characterdetail.Effect;
import com.perpy.controllers.characterdetail.State;
import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.controllers.commons.ProcessedMarvelComic;
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.controllers.commons.ProcessedMarvelItemBase;
import com.perpy.controllers.commons.ProcessedMarvelSeries;
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.entitiy.models.logs.Logger;
import com.perpy.ui.R;
import com.perpy.ui.databinding.FragmentCharacterDetailsBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailFragment extends Fragment {
    @Inject
    CharacterDetailViewModel viewModel;

    int characterId;
    String characterName;
    ImageView imageView;
    RecyclerView rvComics, rvSeries, rvStories, rvEvents;

    FragmentCharacterDetailsBinding binding;
    Logger logger;
    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);
        ProcessedMarvelCharacter item = (ProcessedMarvelCharacter)CharacterDetailFragmentArgs.fromBundle(getArguments()).getItem();
        characterId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        imageView = binding.getRoot().findViewById(R.id.image);
        rvComics = binding.getRoot().findViewById(R.id.rvComics);
        rvSeries = binding.getRoot().findViewById(R.id.rvSeries);
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
        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setTransitionName(transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
        postponeEnterTransition();
        ViewGroup parentView = (ViewGroup) view.getParent();
        parentView.getViewTreeObserver()
                .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                    @Override
                    public boolean onPreDraw() {
                        parentView.getViewTreeObserver().removeOnPreDrawListener(this);
                        startPostponedEnterTransition();
                        return true;
                    }
                });
    }

    void setUpRecyclerView() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvComics.setLayoutManager(layoutManager);

        GridLayoutManager layoutManager2 = new GridLayoutManager(getContext(),1);
        layoutManager2.setOrientation(RecyclerView.HORIZONTAL);
        rvSeries.setLayoutManager(layoutManager2);

        GridLayoutManager layoutManager3 = new GridLayoutManager(getContext(),1);
        layoutManager3.setOrientation(RecyclerView.HORIZONTAL);
        rvStories.setLayoutManager(layoutManager3);

        GridLayoutManager layoutManager4 = new GridLayoutManager(getContext(),1);
        layoutManager4.setOrientation(RecyclerView.HORIZONTAL);
        rvEvents.setLayoutManager(layoutManager4);
    }

    void render(State state) {
        //Log.d("Vartika3", "render: "+state.getComics());
        characterName = state.getCharacter().name;
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if(effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        } else if(effect instanceof Effect.ClickCardEffect) {

            Log.d("Vartika", "clickCardEffect: " + ((ProcessedMarvelItemBase) ((Effect.ClickCardEffect) effect).item).id);
            Effect.ClickCardEffect clickCardEffect = (Effect.ClickCardEffect) effect;
            ProcessedMarvelItemBase item = (ProcessedMarvelItemBase) clickCardEffect.item;
            if (item instanceof ProcessedMarvelComic) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.comicImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToComicDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelSeries) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.seriesImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToSeriesDetailFragment((ProcessedMarvelItemBase) item, item.title ), extras);
                }
            } else if (item instanceof ProcessedMarvelStory) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.storyImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToStoryDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelEvent) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.eventImage);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Image));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEventDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
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
