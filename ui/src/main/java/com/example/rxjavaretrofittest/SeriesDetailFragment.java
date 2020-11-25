package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.controllers.commons.ProcessedMarvelSeries;
import com.example.ui.R;

public class SeriesDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProcessedMarvelSeries item = (ProcessedMarvelSeries) ComicDetailFragmentArgs.fromBundle(getArguments()).getItem();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_series_detail, container, false);
    }
}