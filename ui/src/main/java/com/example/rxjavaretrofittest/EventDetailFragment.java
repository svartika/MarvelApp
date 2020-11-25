package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.controllers.commons.ProcessedMarvelEvent;
import com.example.ui.R;

public class EventDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProcessedMarvelEvent item = (ProcessedMarvelEvent) ComicDetailFragmentArgs.fromBundle(getArguments()).getItem();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_event_detail, container, false);
    }
}