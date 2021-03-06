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
import com.perpy.controllers.commons.ProcessedMarvelStory;
import com.perpy.ui.BR;
import com.perpy.ui.R;
import com.perpy.ui.databinding.StoryRvItemBinding;

import java.util.List;

public class StoriesDetailAdapter extends RecyclerView.Adapter<StoriesDetailAdapter.StoryViewHolder> {
    CardClickListener clickListener;
    Screen screen;
    TransitionNaming transitionNaming = new TransitionNamingImpl();
    public StoriesDetailAdapter(CardClickListener clickListener, Screen screen) {
        this.clickListener = clickListener;
        this.screen = screen;
    }
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
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.storyImage),
                    transitionNaming.getStartAnimationTag(screen, Listing.Stories, ViewElement.Image, String.valueOf(story.id)));
            binding.setVariable(BR.storyItem, story);
            binding.setVariable(BR.cardClickedListener, clickListener);
            binding.executePendingBindings();
        }
    }
}
