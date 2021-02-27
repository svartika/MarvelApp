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
import com.perpy.controllers.commons.ProcessedMarvelEvent;
import com.perpy.ui.BR;
import com.perpy.ui.R;
import com.perpy.ui.databinding.EventRvItemBinding;

import java.util.List;

public class EventsDetailAdapter extends RecyclerView.Adapter<EventsDetailAdapter.EventViewHolder> {
    Screen screen;
    TransitionNaming transitionNaming = new TransitionNamingImpl();
    CardClickListener clickListener;
    public EventsDetailAdapter(CardClickListener clickListener, Screen screen) {
        this.clickListener = clickListener;
        this.screen = screen;
    }

    private final AsyncListDiffer<ProcessedMarvelEvent> differ = new AsyncListDiffer<ProcessedMarvelEvent>(this, differCallback);
    public static final DiffUtil.ItemCallback<ProcessedMarvelEvent> differCallback = new DiffUtil.ItemCallback<ProcessedMarvelEvent>() {
        @Override
        public boolean areItemsTheSame(@NonNull ProcessedMarvelEvent oldItem, @NonNull ProcessedMarvelEvent newItem) {
            return oldItem.id==newItem.id;
        }

        @Override
        public boolean areContentsTheSame(@NonNull ProcessedMarvelEvent oldItem, @NonNull ProcessedMarvelEvent newItem) {
            return oldItem.title.compareToIgnoreCase(newItem.title)==0;
        }
    };

    public void submitList(List<ProcessedMarvelEvent> eventList) {
        differ.submitList(eventList);
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        EventRvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.event_rv_item, parent, false);
        return new EventViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        ProcessedMarvelEvent event = differ.getCurrentList().get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return differ.getCurrentList().size();
    }

    public class EventViewHolder  extends RecyclerView.ViewHolder {
        private final ViewDataBinding binding;
        public EventViewHolder(ViewDataBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }
        public void bind(ProcessedMarvelEvent event) {
            ViewCompat.setTransitionName(binding.getRoot().findViewById(R.id.eventImage),
                    transitionNaming.getStartAnimationTag(screen, Listing.Events, ViewElement.Image, String.valueOf(event.id)));
            binding.setVariable(BR.eventItem, event);
            binding.setVariable(BR.cardClickedListener, clickListener);
            binding.executePendingBindings();
        }
    }
}
