package com.ryanrvldo.movieslibrary.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.ryanrvldo.movieslibrary.R
import com.ryanrvldo.movieslibrary.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.mainToolbar)

        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.main_nav_host) as NavHostFragment
        val navController = navHostFragment.navController
        binding.mainToolbar.setupWithNavController(navController)
    }
}
