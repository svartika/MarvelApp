package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.controllers.commons.ProcessedMarvelComic;
import com.example.ui.R;

public class ComicDetailFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        ProcessedMarvelComic item = (ProcessedMarvelComic) ComicDetailFragmentArgs.fromBundle(getArguments()).getItem();
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_comic_detail, container, false);
    }
}