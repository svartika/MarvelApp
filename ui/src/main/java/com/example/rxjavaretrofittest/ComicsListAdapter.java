package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.retrofit.ProcessedCollection;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.ComicsRvItemBinding;

import java.util.List;

public class ComicsListAdapter extends RecyclerView.Adapter<ComicsListAdapter.ComicsViewHolder> {

    private final AsyncListDiffer<ProcessedCollection.ProcessedItem> differ = new AsyncListDiffer<ProcessedCollection.ProcessedItem>(this, diffCallback);
    public static final DiffUtil.ItemCallback<ProcessedCollection.ProcessedItem>diffCallback = new DiffUtil.ItemCallback<ProcessedCollection.ProcessedItem>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProcessedCollection.ProcessedItem oldItem, @NonNull ProcessedCollection.ProcessedItem newItem) {
            return oldItem.name.compareToIgnoreCase(newItem.name)==0;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedCollection.ProcessedItem oldItem, @NonNull ProcessedCollection.ProcessedItem newItem) {
            return oldItem.name.compareToIgnoreCase(newItem.name)==0;
        }
    };

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComicsRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.comics_rv_item, parent, false);
        return new ComicsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        ProcessedCollection.ProcessedItem comic = differ.getCurrentList().get(position);
        holder.bind(comic);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<ProcessedCollection.ProcessedItem> list) {
        differ.submitList(list);
    }

    public class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public ComicsViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ProcessedCollection.ProcessedItem comicItem) {
            binding.setVariable(BR.comicItem, comicItem);
            binding.executePendingBindings();
        }
    }
}
