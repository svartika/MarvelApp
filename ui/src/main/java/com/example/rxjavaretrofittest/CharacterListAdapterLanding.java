package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.commons.CardClickListener;
import com.example.controllers.commons.ProcessedMarvelCharacter;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.LandingCharacterRvItemBindingImpl;

import java.util.List;

public class CharacterListAdapterLanding extends RecyclerView.Adapter<CharacterListAdapterLanding.MarvelCharacterViewHolder> {

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

    public CharacterListAdapterLanding(CardClickListener marvelCharacterClickedListener) {
        this.marvelCharacterClickedListener = marvelCharacterClickedListener;
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
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.mCharacterImage), String.valueOf(obj.id));
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.mCharacter), obj.id+obj.name.replaceAll(" ", ""));
            binding.setVariable(BR.marvelItem, obj);
            binding.setVariable(BR.clickHandler, marvelCharacterClickedListener);
            binding.executePendingBindings();
        }
    }
}
