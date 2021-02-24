package com.example.marvelapp;

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
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.comicdetail.ComicDetailViewModel;
import com.example.controllers.comicdetail.Effect;
import com.example.controllers.comicdetail.State;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentComicDetailBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ComicDetailFragment extends Fragment {

    @Inject
    ComicDetailViewModel viewModel;

    int comicId;
    String comicName;
    TextView name;
    ImageView imageView;
    RecyclerView rvCharacters, rvSeries, rvStories, rvEvents;

    FragmentComicDetailBinding binding;
    @Inject
    Logger logger;

    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_comic_detail, container, false);
        ProcessedMarvelComic item = (ProcessedMarvelComic) ComicDetailFragmentArgs.fromBundle(getArguments()).getItem();

        comicId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        name = binding.getRoot().findViewById(R.id.name);
        imageView = binding.getRoot().findViewById(R.id.image);
        rvCharacters = binding.getRoot().findViewById(R.id.rvCharacters);
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
        imageView.setTransitionName(transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Image));
        name.setTransitionName(transitionNaming.getEndAnimationTag(Screen.ComicDetail, ViewElement.Name));

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
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvCharacters.setLayoutManager(layoutManager);

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
        comicName = state.getComic().title;
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
            if (item instanceof ProcessedMarvelCharacter) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.ComicDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.mCharacterImage);
                    TextView textView =clickCardEffect.view.findViewById(R.id.mCharacter);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
                    map.put(textView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Name));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(ComicDetailFragmentDirections.actionComicDetailFragmentToCharacterDetailFragment((ProcessedMarvelItemBase) item, ((ProcessedMarvelCharacter) item).name), extras);
                }
            } else if (item instanceof ProcessedMarvelSeries) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.ComicDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.seriesImage);
                    TextView textView =clickCardEffect.view.findViewById(R.id.seriesName);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Image));
                    map.put(textView, transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Name));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(ComicDetailFragmentDirections.actionComicDetailFragmentToSeriesDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelStory) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.ComicDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.storyImage);
                    TextView textView =clickCardEffect.view.findViewById(R.id.storyName);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Image));
                    map.put(textView, transitionNaming.getEndAnimationTag(Screen.StoriesDetail, ViewElement.Name));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(ComicDetailFragmentDirections.actionComicDetailFragmentToStoryDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
                }
            } else if (item instanceof ProcessedMarvelEvent) {
                if (NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.ComicDetailFragment) {
                    ImageView imageView = clickCardEffect.view.findViewById(R.id.eventImage);
                    TextView textView =clickCardEffect.view.findViewById(R.id.eventName);
                    Map<View, String> map = new HashMap<>();
                    map.put(imageView, transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Image));
                    map.put(textView, transitionNaming.getEndAnimationTag(Screen.EventDetail, ViewElement.Name));
                    FragmentNavigator.Extras extras =  new FragmentNavigator.Extras.Builder().addSharedElements(map).build();
                    NavHostFragment.findNavController(this).navigate(ComicDetailFragmentDirections.actionComicDetailFragmentToEventDetailFragment((ProcessedMarvelItemBase) item, item.title), extras);
                }
            }
        } else if(effect instanceof Effect.OpenUrl) {
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(((Effect.OpenUrl) effect).url));
            startActivity(intent);
        }
    }
}