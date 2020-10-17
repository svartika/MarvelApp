package com.example.rxjavaretrofittest.sample;

import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelStore;

import com.example.sample.mvi.viewmodel.Effect;
import com.example.sample.mvi.viewmodel.SampleViewModel;
import com.example.sample.mvi.viewmodel.SampleViewModelImpl;
import com.example.sample.mvi.viewmodel.State;
import com.example.sample.mvi.viewmodel.SampleViewModelFactory;
import com.example.ui.R;
import com.example.ui.databinding.ActivitySampleBinding;

public class SampleActivity2 extends AppCompatActivity {
    SampleViewModel viewModel;
    ActivitySampleBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = init();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_sample);
        setSupportActionBar(findViewById(R.id.toolbar));


        viewModel.getState().observe(this, new Observer<State>() {
            @Override
            public void onChanged(State state) {
                render(state);
            }
        });

        viewModel.getEffect().observe(this, new Observer<Effect>() {
            @Override
            public void onChanged(Effect effect) {
                consume(effect);
            }
        });
    }

    SampleViewModel init() {
        SampleViewModelFactory viewModelFactory = new SampleViewModelFactory(this, 1,getIntent().getExtras());
        ViewModelStore viewModelStore = getViewModelStore();
        ViewModelProvider viewModelProvider= new ViewModelProvider(viewModelStore, viewModelFactory);
        return viewModelProvider.get(SampleViewModelImpl.class);
    }

    void render(State state) {
        binding.setState(state);
    }

    void consume(Effect effect) {
        if(effect instanceof Effect.ToastMe) {
            Toast.makeText(this, "Clicked", Toast.LENGTH_SHORT).show();
        }
    }
}
