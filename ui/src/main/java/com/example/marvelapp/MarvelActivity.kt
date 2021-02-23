package com.example.marvelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.example.ui.R

import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MarvelActivity: AppCompatActivity() {
    val navController by lazy{
        (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /* Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/setUpToolBar()
    }

    private fun setUpToolBar() {


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        val toolbar = findViewById<Toolbar>(R.id.toolbar)
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration)

    }

    fun listen() {
        navController.addOnDestinationChangedListener(object: OnDestinationChangedListener{
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                when(destination.id) {
                    
                }
                R.id.SeriesDetailFragment
            }
        })
        R.id.SeriesDetailFragment
    }

    /*  @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
        //return super.onSupportNavigateUp();
    }*/
}