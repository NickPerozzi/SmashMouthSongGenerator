package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentLyricDisplayBinding
import kotlinx.coroutines.flow.first
import org.koin.androidx.viewmodel.ext.android.viewModel

class LyricDisplayFragment : Fragment() {

    private val ldViewModel: LyricDisplayViewModel by viewModel()

    private lateinit var navController: NavController
    private lateinit var binding: FragmentLyricDisplayBinding
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLyricDisplayBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "settings")

        navController = Navigation.findNavController(view)

        var latestLyrics: String?
        var songTitle: String

        val titleEditText = binding.songTitleEditText
        val lyricsEditText = binding.lyricDisplayEditText

        lifecycleScope.launchWhenCreated {
            latestLyrics = read("recently generated lyrics")
            lyricsEditText.setText(latestLyrics ?: resources.getString(R.string.lyrics_go_here))
        }

        val saveLocallyButton = binding.saveLocallyButton
        saveLocallyButton.setOnClickListener {
            songTitle = titleEditText.text.toString()
            if (songTitle.isEmpty()) {
                Toast.makeText(
                    context, resources.getString(R.string.give_your_song_a_name),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            latestLyrics = lyricsEditText.text.toString()
            latestLyrics?.let {
                if (it.isEmpty()) {
                    Toast.makeText(
                        context, resources.getString(R.string.there_are_no_lyrics),
                        Toast.LENGTH_SHORT
                    ).show()
                    return@setOnClickListener
                }
                val songToSave = SavedSong(0, songTitle, it)
                ldViewModel.insertDataToDatabase(songToSave)
            }
            if (latestLyrics == resources.getString(R.string.lyrics_go_here)) {
                Toast.makeText(
                    context, resources.getString(R.string.you_need_to_generate_lyrics),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val action = LyricDisplayFragmentDirections
                .actionLyricDisplayFragmentToSavedSongsFragment()
            navController.navigate(action)
        }

        binding.generateAgainButton.setOnClickListener { navController.popBackStack() }

        binding.copyLyrics.setOnClickListener {
            songTitle = titleEditText.text.toString()
            latestLyrics = lyricsEditText.text.toString()
            latestLyrics?.let { copyToClipboard(requireActivity(), songTitle, it) }
        }

        ldViewModel.addToDatabaseStatus.observe(viewLifecycleOwner, {
            if (it) { Toast.makeText(requireContext(), resources.getString(R.string.song_saved), Toast.LENGTH_SHORT).show()
            }
        })

    }

    private fun copyToClipboard(fragActivity: FragmentActivity, title: String, lyrics: String) {
        val clipboard = fragActivity.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText(title, lyrics)
        clipboard.setPrimaryClip(clip)
        Toast.makeText(
            context, resources.getString(R.string.copied_to_clipboard),
            Toast.LENGTH_SHORT
        ).show()
    }

    private suspend fun read(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }


}