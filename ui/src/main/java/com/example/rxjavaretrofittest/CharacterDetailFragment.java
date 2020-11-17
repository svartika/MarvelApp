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
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.characterdetail.CharacterDetailViewModel;
import com.example.controllers.characterdetail.Effect;
import com.example.controllers.characterdetail.State;
import com.example.controllers.commons.ProcessedMarvelCharacter;
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
    RecyclerView rvComics, rvComicsDetailed;

    FragmentCharacterDetailsBinding binding;

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
        rvComicsDetailed = binding.getRoot().findViewById(R.id.rvComicsDetailed);
        setUpRecyclerView();
        setUpRecyclerViewDetailed();

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

    }
    void setUpRecyclerViewDetailed() {
        GridLayoutManager layoutManager = new GridLayoutManager(getContext(),1);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        rvComicsDetailed.setLayoutManager(layoutManager);

    }

    void render(State state) {
        Log.d("Vartika3", "render: "+state.getComics());
        binding.setState(state);
        binding.executePendingBindings();
    }

    void consume(Effect effect) {
        if(effect instanceof Effect.ImageLoaded) {
            startPostponedEnterTransition();
        }
    }
}
