package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entitiy.models.MarvelCharacter;
import com.example.ui.R;

import java.util.List;

import javax.inject.Inject;

public class MarvelCharacterListAdapter extends RecyclerView.Adapter<MarvelCharacterListAdapter.ChangeViewHolder> {

    @Inject
    MarvelCharacterListAdapter() {    }

    List<MarvelCharacter> marvelCharacters;
    void setMarvelCharacters(List<MarvelCharacter> marvelCharacters) {
        this.marvelCharacters = marvelCharacters;
    }
    @NonNull
    @Override
    public ChangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marvel_character_rv_item, parent, false);

        return new ChangeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeViewHolder holder, int position) {
        holder.mCharacter.setText(marvelCharacters.get(position).name);
    }

    @Override
    public int getItemCount() {
        if(marvelCharacters==null || marvelCharacters.size()==0) return 0;
        return marvelCharacters.size();
    }

    class ChangeViewHolder extends RecyclerView.ViewHolder {
        public TextView mCharacter;
        public ChangeViewHolder(@NonNull View itemView) {
            super(itemView);
            mCharacter = itemView.findViewById(R.id.mCharacter);
        }
    }
}
