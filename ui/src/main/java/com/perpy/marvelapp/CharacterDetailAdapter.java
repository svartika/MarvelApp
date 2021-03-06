package com.perpy.marvelapp;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.perpy.controllers.commons.CardClickListener;
import com.perpy.controllers.commons.ProcessedMarvelCharacter;
import com.perpy.ui.BR;
import com.perpy.ui.R;
import com.perpy.ui.databinding.CharacterRvItemBindingImpl;

import java.util.List;

public class CharacterDetailAdapter extends RecyclerView.Adapter<CharacterDetailAdapter.MarvelCharacterViewHolder> {

    CardClickListener marvelCharacterClickedListener;
    TransitionNaming transitionNaming = new TransitionNamingImpl();
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

    public CharacterDetailAdapter(CardClickListener marvelCharacterClickedListener, Screen screen) {
        this.marvelCharacterClickedListener = marvelCharacterClickedListener;
        this.screen = screen;
    }
    Screen screen;
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
       CharacterRvItemBindingImpl binding = DataBindingUtil.inflate(inflater, R.layout.character_rv_item, parent, false);
       return new MarvelCharacterViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MarvelCharacterViewHolder holder, int position) {
        //holder.mCharacter.setText(marvelCharacters.get(position).name);
        ProcessedMarvelCharacter marvelCharacter = differ.getCurrentList().get(position);
        holder.bind(marvelCharacter);
        //holder.mCharacter.setText(marvelCharacter.name);
    }

    @Override
    public int getItemCount() {
       /* if(marvelCharacters==null || marvelCharacters.size()==0) return 0;
        return marvelCharacters.size();*/
        return differ.getCurrentList().size();
    }

    public void submitList(List<ProcessedMarvelCharacter> list) {
        differ.submitList(list);
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
    }
}
