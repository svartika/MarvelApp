package com.example.rxjavaretrofittest;

import android.os.Bundle;
import android.widget.FrameLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ui.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CharactersListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

}