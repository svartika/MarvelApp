package com.example.rxjavaretrofittest;

import android.view.View;

import androidx.databinding.BindingAdapter;

import com.example.mviframework.Runner;

public class SampleBindingUtils {
    @BindingAdapter(value = {"clickRunner"})
    public static void clickRunner(View view, Runner runner) {
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                runner.run();
            }
        });
    }

}
