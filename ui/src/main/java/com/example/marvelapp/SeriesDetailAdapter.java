package com.example.marvelapp;

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
import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.ui.BR;
import com.example.ui.R;
import com.example.ui.databinding.SeriesRvItemBinding;

import java.util.List;

public class SeriesDetailAdapter extends RecyclerView.Adapter<SeriesDetailAdapter.SeriesViewHolder> {
    CardClickListener clickListener;
    TransitionNaming transitionNaming = new TransitionNamingImpl();
    Screen screen;
    public SeriesDetailAdapter(CardClickListener clickListener, Screen screen) {
        this.clickListener = clickListener;
        this.screen = screen;
    }
    private final AsyncListDiffer<ProcessedMarvelSeries> differ = new AsyncListDiffer<ProcessedMarvelSeries>(this, differCallback);
    public static final DiffUtil.ItemCallback<ProcessedMarvelSeries> differCallback = new DiffUtil.ItemCallback<ProcessedMarvelSeries>() {

        @Override
        public boolean areItemsTheSame(@NonNull ProcessedMarvelSeries oldItem, @NonNull ProcessedMarvelSeries newItem) {
            return oldItem.id == newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedMarvelSeries oldItem, @NonNull ProcessedMarvelSeries newItem) {
            return oldItem.title.compareToIgnoreCase(newItem.title)==0;
        }
    };

    public void submitList(List<ProcessedMarvelSeries> seriesList) {
        differ.submitList(seriesList);
    }

    @NonNull
    @Override
    public SeriesViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        SeriesRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.series_rv_item, parent, false);
        return new SeriesViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull SeriesViewHolder holder, int position) {
        ProcessedMarvelSeries seriesItem = differ.getCurrentList().get(position);
        holder.bind(seriesItem);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }


    class SeriesViewHolder extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public SeriesViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ProcessedMarvelSeries seriesItem) {
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.seriesImage),
                    transitionNaming.getStartAnimationTag(screen, Listing.Series, ViewElement.Image, String.valueOf(seriesItem.id))
            );
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.seriesName),
                    transitionNaming.getStartAnimationTag(screen, Listing.Series, ViewElement.Name, String.valueOf(seriesItem.id)));
            binding.setVariable(BR.seriesItem, seriesItem);
            binding.setVariable(BR.cardClickedListener, clickListener);
            binding.executePendingBindings();
        }
    }
}
