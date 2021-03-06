package com.perpy.marvelapp;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.transition.TransitionInflater;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.EditText;
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

import com.perpy.controllers.characterslist.CharactersListViewModel;
import com.perpy.controllers.characterslist.Effect;
import com.perpy.controllers.characterslist.SearchTextChangedCallbackListener;
import com.perpy.controllers.characterslist.State;
import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.ui.R;
import com.perpy.ui.databinding.FragmentCharacterListBinding;

import java.util.HashMap;
import java.util.Map;

import javax.inject.Inject;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharacterListFragment extends Fragment {
    @Inject
    CharactersListViewModel viewModel;
    FragmentCharacterListBinding binding;
    RecyclerView rvMarvelCharacters;
    EditText searchView;
    SearchTextChangedCallbackListener searchCallback;
    ViewGroup parentView;
    TransitionNaming transitionNaming = new TransitionNamingImpl();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
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

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_character_list, container, false);
        rvMarvelCharacters = binding.getRoot().findViewById(R.id.rvMarvelCharacters);
        setUpRecyclerView();
        searchView = binding.getRoot().findViewById(R.id.searchView);
        setUpSearchView();
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
        binding.getRoot().setOnKeyListener( new View.OnKeyListener()
        {
            @Override
            public boolean onKey( View v, int keyCode, KeyEvent event )
            {
                if( keyCode == KeyEvent.KEYCODE_BACK )
                {
                    if(searchView.getText().length()>0) {
                        searchView.setText("");
                        return true;
                    }
                }
                return false;
            }
        } );
        return binding.getRoot();
    }
    void setUpRecyclerView() {
        DisplayMetrics displayMetrics = new DisplayMetrics();
        getActivity().getWindowManager().getDefaultDisplay().getMetrics(displayMetrics);
        int width = displayMetrics.widthPixels;
        int span = width / getResources().getDimensionPixelSize(R.dimen.character_image_width_landing);
        Log.d("VartikaHilt", "Width and span " + width + span);
        rvMarvelCharacters.setLayoutManager(new GridLayoutManager(getContext(), span));
    }
    void setUpSearchView() {
        searchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if(searchCallback!=null)
                    searchCallback.invoke(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
    }

    void consume(Effect effect) {
        if (effect instanceof Effect.ClickCharacterEffect) {
            Effect.ClickCharacterEffect clickEffect = ((Effect.ClickCharacterEffect) effect);
            ProcessedMarvelCharacter marvelCharacter = (ProcessedMarvelCharacter) clickEffect.item;
            ImageView imageView = clickEffect.view.findViewById(R.id.mCharacterImage);
            TextView textView = clickEffect.view.findViewById(R.id.mCharacter);
            Map<View,String> map = new HashMap<>();
            map.put(imageView, transitionNaming.getEndAnimationTag(Screen.CharacterDetail, ViewElement.Image));
            FragmentNavigator.Extras extras = new FragmentNavigator.Extras.Builder()
                    .addSharedElements(map)
                    /*.addSharedElement(clickEffect.view.findViewById(R.id.mCharacter), "marvelTransitionName")*/
                    .build();
            CharacterListFragmentDirections.ActionListToDetail directions = CharacterListFragmentDirections.actionListToDetail(marvelCharacter, marvelCharacter.name);
            if(NavHostFragment.findNavController(CharacterListFragment.this).getCurrentDestination().getId()==R.id.CharactersListFragment)
                NavHostFragment.findNavController(CharacterListFragment.this).navigate(directions, extras);

        }
    }

    void render(State state) {
        searchCallback = state.getSearchCallback();

        binding.setState(state);
        binding.executePendingBindings();
    }

}
