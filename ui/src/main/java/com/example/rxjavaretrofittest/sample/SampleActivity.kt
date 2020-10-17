package com.example.rxjavaretrofittest.sample

import android.os.Bundle
import android.widget.TextView
import androidx.activity.viewModels
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.example.sample.mvi.viewmodel.SampleViewModel
import com.example.sample.mvi.viewmodel.State
import com.example.sample.mvi.viewmodel.SampleViewModelFactory
import com.example.ui.R


class SampleActivity : AppCompatActivity() {
    val viewModel: SampleViewModel by viewModels{
        SampleViewModelFactory(this, 1, intent.extras)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample)
        setSupportActionBar(findViewById(R.id.toolbar))

        findViewById<FloatingActionButton>(R.id.fab).setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }
        viewModel.state.observe(this, Observer {
            it.render()
        })
    }

    fun State.render() {
        findViewById<TextView>(R.id.textView).text = id.toString();
    }
}