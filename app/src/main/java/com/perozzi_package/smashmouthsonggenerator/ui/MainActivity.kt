package com.perozzi_package.smashmouthsonggenerator.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        // val navController = findNavController(R.id.nav_host_fragment_container_view)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.ic_generate_sounds,
                R.id.ic_latest_generated,
                R.id.ic_saved_sounds,
                R.id.ic_about_page
            )
        )

        val navMenu = binding.navigationMenu
        supportActionBar?.hide()
        setupActionBarWithNavController(navController, appBarConfiguration)
        navMenu.setupWithNavController(navController)
    }
}