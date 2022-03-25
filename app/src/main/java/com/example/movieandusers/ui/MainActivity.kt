package com.example.movieandusers.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.movieandusers.R
import com.example.movieandusers.databinding.ActivityMainBinding

const val REQUEST_CODE_LOCATION = 617254

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initToolbar()
        initViews()

    }

    private fun initViews() {
        with(binding){
            val navController = findNavController(R.id.navigation_host_fragment)
            navigationView.setupWithNavController(navController)
        }
    }

    private fun initToolbar() {
        setSupportActionBar(binding.toolbar)
    }
}