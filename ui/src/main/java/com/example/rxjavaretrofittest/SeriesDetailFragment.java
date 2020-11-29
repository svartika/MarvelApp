package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.controllers.seriesdetail.Effect;
import com.example.controllers.seriesdetail.SeriesDetailViewModel;
import com.example.controllers.seriesdetail.State;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentSeriesDetailBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class SeriesDetailFragment extends Fragment {

    @Inject
    SeriesDetailViewModel viewModel;

    int seriesId;
    TextView name;
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
        ProcessedMarvelSeries item = (ProcessedMarvelSeries) ComicDetailFragmentArgs.fromBundle(getArguments()).getItem();
        seriesId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        name = binding.getRoot().findViewById(R.id.name);
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
        name.setTransitionName(transitionNaming.getEndAnimationTag(Screen.SeriesDetail, ViewElement.Name));
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
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if(effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        } else if(effect instanceof Effect.CardClickedEffect) {
            ProcessedMarvelItemBase item = (ProcessedMarvelItemBase)((Effect.CardClickedEffect) effect).item;
            if(item instanceof ProcessedMarvelCharacter) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId() == R.id.SeriesDetailFragment)
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToCharacterDetailFragment(item));
            } else if( item instanceof ProcessedMarvelComic) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment)
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToComicDetailFragment(item));
            } else if(item instanceof ProcessedMarvelStory) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment)
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToStoryDetailFragment(item));
            } else if(item instanceof ProcessedMarvelEvent) {
                if(NavHostFragment.findNavController(this).getCurrentDestination().getId()==R.id.SeriesDetailFragment)
                    NavHostFragment.findNavController(this).navigate(SeriesDetailFragmentDirections.actionSeriesDetailFragmentToEventDetailFragment(item));
            }
        }
    }
}