package com.example.rxjavaretrofittest;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.OneShotPreDrawListener;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavDirections;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.retrofit.AbsCharactersListPageController;
import com.example.controllers.retrofit.CharactersListPageController;
import com.example.controllers.retrofit.Effect;
import com.example.controllers.retrofit.ProcessedMarvelCharacter;
import com.example.entitiy.models.logs.Logger;
import com.example.ui.R;
import com.example.ui.databinding.FragmentCharactersListBinding;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharactersListFragment extends Fragment {

    @Inject
    AbsCharactersListPageController controller;
    RecyclerView rvMarvelCharacters;
    EditText searchView;

    FragmentCharactersListBinding binding;
    @Inject
    Logger logger;
    ViewGroup parentView;

    @Override
    public View onCreateView(
            LayoutInflater inflater, ViewGroup container,
            Bundle savedInstanceState
    ) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_characters_list, container, false);
        rvMarvelCharacters = binding.getRoot().findViewById(R.id.rvMarvelCharacters);
        searchView = binding.getRoot().findViewById(R.id.searchView);
        setUpRecyclerView();

        setUpSearchView();


        setSharedElementReturnTransition(TransitionInflater.from(getContext()).inflateTransition(android.R.transition.move));
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        controller.stateLiveData().observe(getViewLifecycleOwner(), state -> {
            setState(state);
        });
        controller.effectLiveData().observe(getViewLifecycleOwner(), effect -> {
            setEffect(effect);
        });

        postponeEnterTransition();
        parentView = (ViewGroup) view.getParent();
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

    public CharactersListFragment() {

    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    void setUpSearchView() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                controller.searchCharacter(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    void setUpRecyclerView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width / getResources().getDimensionPixelSize(R.dimen.character_image_width);
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(getContext(), span));
    }

    private void setState(CharactersListPageController.State state) {
        Log.d("Vartika", "setState: " + "error: " + state.error + "size: " + state.marvelCharactersList.size());
        binding.setState(state);

        /*if(state.error!=false) {
            parentView.getViewTreeObserver()
                        .addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener() {
                            @Override
                            public boolean onPreDraw() {
                                //parentView.getViewTreeObserver().removeOnPreDrawListener(this);
                                startPostponedEnterTransition();
                                return true;
                            }
                        });
        }*/
    }

    private void setEffect(Effect effect) {
        if (effect instanceof AbsCharactersListPageController.ClickEffect) {
            AbsCharactersListPageController.ClickEffect clickEffect = ((AbsCharactersListPageController.ClickEffect) effect);
            ProcessedMarvelCharacter marvelCharacter = (ProcessedMarvelCharacter) clickEffect.item;
            ImageView imageView = clickEffect.view.findViewById(R.id.mCharacterImage);
            FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                    .addSharedElement(imageView, "marvelTransition")
                    .build();
            CharactersListFragmentDirections.ActionListToDetail directions = CharactersListFragmentDirections.actionListToDetail(marvelCharacter);
            Navigation.findNavController(getView()).navigate(directions, extras);


           /* NavHostFragment.findNavController(CharactersListFragment.this)
                    .navigate(R.id.action_FirstFragment_to_SecondFragment);*/



           /* Intent intent = new Intent(getActivity(), CharacterDetailsActivity.class);
            intent.putExtra("MARVEL_CHARACTER_ID", marvelCharacter.id);
            startActivity(intent);*/

        }
    }
}