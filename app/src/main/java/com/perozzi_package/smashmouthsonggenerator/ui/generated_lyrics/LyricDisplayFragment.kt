package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.copyToClipboard
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentLyricDisplayBinding
import com.perozzi_package.smashmouthsonggenerator.hideKeyboard
import org.koin.androidx.viewmodel.ext.android.viewModel

class LyricDisplayFragment : Fragment() {

    private val ldViewModel: LyricDisplayViewModel by viewModel()

    private lateinit var navController: NavController
    private lateinit var binding: FragmentLyricDisplayBinding

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

        navController = Navigation.findNavController(view)

        var latestLyrics: String? // nullable because DataStore requires nullability
        var songTitle: String

        val titleEditText = binding.songTitleEditText
        val lyricsEditText = binding.lyricDisplayEditText

        lifecycleScope.launchWhenCreated {
            latestLyrics = ldViewModel.readFromDataStore("recently generated lyrics")
            lyricsEditText.setText(latestLyrics ?: resources.getString(R.string.lyrics_go_here))
        }

        binding.saveLocallyButton.setOnClickListener {
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
                ldViewModel.insertDataToDatabase(SavedSong(0, songTitle, it))
            }
            if (latestLyrics == resources.getString(R.string.lyrics_go_here)) {
                Toast.makeText(
                    context, resources.getString(R.string.you_need_to_generate_lyrics),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            hideKeyboard(requireActivity(), view)
            navController.navigate(LyricDisplayFragmentDirections
                .actionLyricDisplayFragmentToSavedSongsFragment())
        }

        binding.generateAgainButton.setOnClickListener { navController.popBackStack() }

        binding.copyLyrics.setOnClickListener {
            songTitle = titleEditText.text.toString()
            latestLyrics = lyricsEditText.text.toString()
            latestLyrics?.let { copyToClipboard(requireActivity(), songTitle, it) }
        }

        ldViewModel.savedToDatabaseIndicator.observe(viewLifecycleOwner, {
            if (it) { Toast.makeText(requireContext(), resources.getString(R.string.song_saved), Toast.LENGTH_SHORT).show()
            }
        })
    }
}