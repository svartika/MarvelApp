package com.perpy.marvelapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.NavController
import androidx.navigation.NavController.OnDestinationChangedListener
import androidx.navigation.NavDestination
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.perpy.ui.R

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
        getSupportActionBar().setDisplayShowHomeEnabled(true);*/

        setUpToolBar()
        listen()
    }

    lateinit var toolbar : Toolbar
    private fun setUpToolBar() {


        //NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()
        toolbar = findViewById<Toolbar>(R.id.toolbar)

      //  actionBar?.setDisplayUseLogoEnabled(true)

        //actionBar?.setDisplayShowCustomEnabled(true)
        //setSupportActionBar(toolbar)
      /*  supportActionBar?.setDisplayUseLogoEnabled(true)
        supportActionBar?.setDisplayShowCustomEnabled(true)*/
        NavigationUI.setupWithNavController(
                toolbar, navController, appBarConfiguration)

    }

    fun listen() {
        navController.addOnDestinationChangedListener(object: OnDestinationChangedListener{
            override fun onDestinationChanged(controller: NavController, destination: NavDestination, arguments: Bundle?) {
                val id = destination.id;
                when(destination.id) {
                    R.id.CharactersListFragment -> toolbar.setLogo(R.drawable.nav_icon)
                    R.id.CharacterDetailFragment -> toolbar.setLogo(null)
                    R.id.ComicDetailFragment -> toolbar.setLogo(null)
                    R.id.SeriesDetailFragment -> toolbar.setLogo(null)
                    R.id.StoryDetailFragment -> toolbar.setLogo(null)
                    R.id.EventDetailFragment -> toolbar.setLogo(null)
                }
            }
        })
    }

    /*  @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
        //return super.onSupportNavigateUp();
    }*/
}