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

import com.example.controllers.retrofit.CharacterDetailPageController;
import com.example.controllers.retrofit.Effect;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharacterDetailsBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterDetailFragment extends Fragment {
    @Inject
    CharacterDetailPageController controller;

    int characterId;
    TextView name;
    ImageView imageView;
    FragmentCharacterDetailsBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_details, container, false);
        /*Intent intent = getActivity().getIntent();
        characterId = intent.getIntExtra("MARVEL_CHARACTER_ID", 0);*/

        ProcessedMarvelCharacter item = CharacterDetailFragmentArgs.fromBundle(getArguments()).getItem();


        characterId = item.id;

        //ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.image), "marvelTransition");
        setSharedElementEnterTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        postponeEnterTransition();//400, TimeUnit.MILLISECONDS);

        name = binding.getRoot().findViewById(R.id.name);
        imageView = binding.getRoot().findViewById(R.id.image);

        controller.getStateLiveData().observe(getViewLifecycleOwner(),
                (state -> {
                    renderState(state);
                }
                ));

        controller.effectLiveData().observe(getViewLifecycleOwner(),
                (effect -> {
                    consumeEffect(effect);
                }));
        loadCharacter();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView.setTransitionName("marvelTransition");
    }

    void loadCharacter() {
       // characterId = 1011334;
        controller.loadCharacterDetails(characterId);
    }

    void renderState(CharacterDetailPageController.State state) {
        //Log.d("VartikaHilt", "marvelCharacter.name->" + state.character.name);
        binding.setState(state);
        binding.executePendingBindings();
     //   startPostponedEnterTransition();
    }

    void consumeEffect(Effect effect) {
        if(effect instanceof CharacterDetailPageController.ImageLoaded) {
            startPostponedEnterTransition();
        }
    }
}
