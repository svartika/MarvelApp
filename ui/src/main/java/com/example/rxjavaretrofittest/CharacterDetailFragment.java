package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.transition.TransitionInflater;
import android.util.Log;
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

import com.example.controllers.characterdetail.CharacterDetailViewModel;
import com.example.controllers.characterdetail.Effect;
import com.example.controllers.characterdetail.State;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.controllers.commons.ProcessedMarvelItemBase;
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharacterDetailsBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailFragment extends Fragment {
    @Inject
    CharacterDetailViewModel viewModel;

    int characterId;
    TextView name;
    ImageView imageView;
    RecyclerView rvComics, rvSeries, rvStories, rvEvents;

    FragmentCharacterDetailsBinding binding;
    Logger logger;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);
        ProcessedMarvelCharacter item = CharacterDetailFragmentArgs.fromBundle(getArguments()).getItem();
        characterId = item.id;

        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();

        name = binding.getRoot().findViewById(R.id.name);
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

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setTransitionName("marvelTransition");
        name.setTransitionName("marvelTransitionName");
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
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment)
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToComicDetailFragment((ProcessedMarvelItemBase) item));
            } else if (item instanceof ProcessedMarvelSeries) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment)
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToSeriesDetailFragment((ProcessedMarvelItemBase) item));
            } else if (item instanceof ProcessedMarvelStory) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment)
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToStoryDetailFragment((ProcessedMarvelItemBase) item));
            } else if (item instanceof ProcessedMarvelEvent) {
                if (NavHostFragment.findNavController(CharacterDetailFragment.this).getCurrentDestination().getId() == R.id.CharacterDetailFragment)
                    NavHostFragment.findNavController(CharacterDetailFragment.this).navigate(CharacterDetailFragmentDirections.actionCharacterDetailFragmentToEventDetailFragment((ProcessedMarvelItemBase) item));
            }
        }
    }
}
