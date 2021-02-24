package com.example.marvelapp;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.paging.PagedListAdapter;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.LandingCharacterRvItemBindingImpl;

public class CharacterListAdapterLanding extends PagedListAdapter<ProcessedMarvelCharacter, CharacterListAdapterLanding.MarvelCharacterViewHolder> {
    TransitionNaming transitionNaming = new TransitionNamingImpl();
    Screen screen;
    CardClickListener marvelCharacterClickedListener;
    private final AsyncListDiffer<ProcessedMarvelCharacter> differ = new AsyncListDiffer<ProcessedMarvelCharacter>(this, diffCallBack);
    public static final DiffUtil.ItemCallback<ProcessedMarvelCharacter> diffCallBack = new DiffUtil.ItemCallback<ProcessedMarvelCharacter>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProcessedMarvelCharacter oldItem, @NonNull ProcessedMarvelCharacter newItem) {
            return (oldItem.name.compareToIgnoreCase(newItem.name) == 0);
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedMarvelCharacter oldItem, @NonNull ProcessedMarvelCharacter newItem) {
            return (oldItem.imageurl.compareToIgnoreCase(newItem.imageurl) == 0);
        }
    };

    public CharacterListAdapterLanding(CardClickListener marvelCharacterClickedListener, Screen screen) {
        super(diffCallBack);
        this.marvelCharacterClickedListener = marvelCharacterClickedListener;
        this.screen = screen;
    }

    // List<ProcessedMarvelCharacter> marvelCharacters;
   /* void setMarvelCharacters(List<ProcessedMarvelCharacter> marvelCharacters) {
        this.marvelCharacters = marvelCharacters;
    }*/
    @NonNull
    @Override
    public MarvelCharacterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
       /* View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marvel_character_rv_item, parent, false);
        return new MarvelCharacterViewHolder(v);*/
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        LandingCharacterRvItemBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.landing_character_rv_item, parent, false);
        return new MarvelCharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarvelCharacterViewHolder holder, int position) {
        ProcessedMarvelCharacter o = getItem(position);
        if (o != null)
            holder.bind(o);
        else {
            holder.bindClean();
        }
    }

    class MarvelCharacterViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;

        public MarvelCharacterViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;

           /* public TextView mCharacter;
            public ImageView mCharacterImage;
            public MarvelCharacterViewHolder(@NonNull View itemView) {
                super(itemView);
                mCharacter = itemView.findViewById(R.id.mCharacter);
                mCharacterImage = itemView.findViewById(R.id.mCharacterImage);*/
        }

        public void bind(ProcessedMarvelCharacter obj) {
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.mCharacterImage),
                    transitionNaming.getStartAnimationTag(screen, Listing.Characters, ViewElement.Image, String.valueOf(obj.id))
            );
            binding.setVariable(BR.marvelItem, obj);
            binding.setVariable(BR.clickHandler, marvelCharacterClickedListener);
            binding.executePendingBindings();
        }

        public void bindClean() {
            binding.setVariable(BR.marvelItem, null);
            binding.setVariable(BR.clickHandler, new CardClickListener<ProcessedMarvelCharacter>() {
                @Override
                public void invoke(View view, ProcessedMarvelCharacter character) {

                }
            });
            binding.executePendingBindings();
        }
    }
}
