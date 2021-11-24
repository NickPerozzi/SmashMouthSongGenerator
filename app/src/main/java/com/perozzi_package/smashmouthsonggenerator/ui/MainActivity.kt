package com.perozzi_package.smashmouthsonggenerator.ui

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.databinding.ActivityMainBinding
import com.perozzi_package.smashmouthsonggenerator.ui.about_page.AboutPageFragment
import com.perozzi_package.smashmouthsonggenerator.ui.album_weights.WeightAssignmentFragment
import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.LyricDisplayFragment
import com.perozzi_package.smashmouthsonggenerator.ui.saved_songs.SavedSongsFragment

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()


        val weightFragment = WeightAssignmentFragment()
        val lyricsFragment = LyricDisplayFragment()
        val savedSongsFragment = SavedSongsFragment()
        val aboutPageFragment = AboutPageFragment()
        val navMenu = binding.navigationMenu

        // TODO(Ask Booni or Kevin about more efficient Bottom Navigation)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container_view) as NavHostFragment
        val navController = navHostFragment.navController
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.ic_generate_sounds,
                R.id.ic_latest_generated,
                R.id.ic_saved_sounds,
                R.id.ic_about_page
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navMenu.setupWithNavController(navController)

        binding.appLogoSome.setOnClickListener {
            val mpSome = MediaPlayer.create(this, R.raw.some)
            mpSome.start()
        }
        binding.appLogoBody.setOnClickListener {
            val mpBody = MediaPlayer.create(this, R.raw.body)
            mpBody.start()
        }
    }
}