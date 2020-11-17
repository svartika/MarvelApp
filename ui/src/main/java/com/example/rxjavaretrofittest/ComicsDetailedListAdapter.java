package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.ComicsDetailedRvItemBinding;

import java.util.List;

public class ComicsDetailedListAdapter extends RecyclerView.Adapter<ComicsDetailedListAdapter.ComicsViewHolder> {

    private final AsyncListDiffer<ProcessedMarvelComic> differ = new AsyncListDiffer<ProcessedMarvelComic>(this, diffCallback);
    public static final DiffUtil.ItemCallback<ProcessedMarvelComic>diffCallback = new DiffUtil.ItemCallback<ProcessedMarvelComic>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProcessedMarvelComic oldItem, @NonNull ProcessedMarvelComic newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedMarvelComic oldItem, @NonNull ProcessedMarvelComic newItem) {
            return oldItem.title.compareToIgnoreCase(newItem.title)==0;
        }
    };

    @NonNull
    @Override
    public ComicsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ComicsDetailedRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.comics_detailed_rv_item, parent, false);
        return new ComicsViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ComicsViewHolder holder, int position) {
        ProcessedMarvelComic comic = differ.getCurrentList().get(position);
        holder.bind(comic);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public void submitList(List<ProcessedMarvelComic> list) {
        differ.submitList(list);
    }

    public class ComicsViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public ComicsViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ProcessedMarvelComic comicItem) {
            binding.setVariable(BR.comicItemDetail, comicItem);
            binding.executePendingBindings();
        }
    }
}
