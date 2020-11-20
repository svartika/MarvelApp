package com.example.rxjavaretrofittest;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.AsyncListDiffer;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.controllers.commons.ProcessedMarvelStory;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.StoryRvItemBinding;

import java.util.List;

public class StoriesListAdapter extends RecyclerView.Adapter<StoriesListAdapter.StoryViewHolder> {
    private final AsyncListDiffer<ProcessedMarvelStory> differ = new AsyncListDiffer<ProcessedMarvelStory>(this, differCallback);
    public static final DiffUtil.ItemCallback<ProcessedMarvelStory> differCallback = new DiffUtil.ItemCallback<ProcessedMarvelStory>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProcessedMarvelStory oldItem, @NonNull ProcessedMarvelStory newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedMarvelStory oldItem, @NonNull ProcessedMarvelStory newItem) {
            return oldItem.title.compareToIgnoreCase(newItem.title)==0;
        }
    };
    public void submitList(List<ProcessedMarvelStory>stories) {
        differ.submitList(stories);
    }

    @NonNull
    @Override
    public StoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        StoryRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.story_rv_item, parent, false);
        return new StoryViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull StoryViewHolder holder, int position) {
        ProcessedMarvelStory story = differ.getCurrentList().get(position);
        holder.bind(story);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }


    public class StoryViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public StoryViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        public void bind(ProcessedMarvelStory story) {
            binding.setVariable(BR.storyItem, story);
            binding.executePendingBindings();
        }
    }
}
