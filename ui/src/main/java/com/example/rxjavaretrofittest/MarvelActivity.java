package com.example.rxjavaretrofittest;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.example.ui.R;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class MarvelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
       /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/
    }

  /*  @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
        //return super.onSupportNavigateUp();
    }*/
}