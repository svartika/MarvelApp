package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.ui.R;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.entitiy.models.Change;

import java.util.List;

public class ChangesAdapter extends RecyclerView.Adapter<ChangesAdapter.ChangeViewHolder> {

    List<Change> changes;
    void setChanges(List<Change> changes) {
        this.changes = changes;
    }
    @NonNull
    @Override
    public ChangeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.marvel_character_rv_item, parent, false);

        return new ChangeViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ChangeViewHolder holder, int position) {
        holder.change.setText(changes.get(position).getSubject());
    }

    @Override
    public int getItemCount() {
        if(changes==null || changes.size()==0) return 0;
        return changes.size();
    }

    class ChangeViewHolder extends RecyclerView.ViewHolder {
        public TextView change;
        public ChangeViewHolder(@NonNull View itemView) {
            super(itemView);
            change = itemView.findViewById(R.id.mCharacter);
        }
    }
}
