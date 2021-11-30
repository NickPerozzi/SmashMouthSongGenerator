package com.perozzi_package.smashmouthsonggenerator.ui.saved_songs

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.FragmentActivity
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSelectedSavedSongBinding

class SelectedSavedSongFragment : Fragment() {

    private val args: SelectedSavedSongFragmentArgs by navArgs()
    private lateinit var binding: FragmentSelectedSavedSongBinding
    private lateinit var sssViewModel: SelectedSavedSongViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedSavedSongBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        sssViewModel = SelectedSavedSongViewModel(requireActivity().application)

        val titleEditText = binding.selectedSongTitle
        val lyricsEditText = binding.selectedSongLyrics

        lyricsEditText.setText(args.savedItemObject.songLyrics)
        titleEditText.setText(args.savedItemObject.songTitle)

        binding.copyButton.setOnClickListener {
            copyToClipboard(
                requireActivity(),
                args.savedItemObject.songTitle,
                args.savedItemObject.songLyrics
            )
        }

        binding.updateButton.setOnClickListener {
            args.savedItemObject.songTitle = titleEditText.text.toString()
            args.savedItemObject.songLyrics = lyricsEditText.text.toString()
            if (args.savedItemObject.songTitle == "") {
                Toast.makeText(
                    context, resources.getString(R.string.give_your_song_a_name),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            if (args.savedItemObject.songLyrics == "") {
                Toast.makeText(
                    context, resources.getString(R.string.you_need_to_generate_lyrics),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            updateDataInDatabase(args.savedItemObject)
            navController.popBackStack()
        }
    }

    private fun updateDataInDatabase(savedSong: SavedSong) {
        sssViewModel.updateSavedSong(savedSong)
        Toast.makeText(
            requireContext(), resources.getString(R.string.song_updated),
            Toast.LENGTH_SHORT
        ).show()
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
}