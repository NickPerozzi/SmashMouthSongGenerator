package com.perozzi_package.smashmouthsonggenerator.ui

import android.os.Bundle
import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.clear
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
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
        supportActionBar?.hide()

        val navMenu = binding.navigationMenu
        val navController = findNavController(R.id.nav_host_fragment)
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.ic_generate_sounds,
                R.id.ic_latest_generated,
                R.id.ic_saved_sounds,
                R.id.ic_about_page
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navMenu.setupWithNavController(navController)

        // Clears the DataStore when the app launches
        val dataStore = createDataStore(name = "settings")
        lifecycleScope.launchWhenCreated { dataStore.edit { it.clear() } }

        binding.logoSome.setOnClickListener { MediaPlayer.create(this, R.raw.some).start() }
        binding.logoBody.setOnClickListener { MediaPlayer.create(this, R.raw.body).start() }
    }
}