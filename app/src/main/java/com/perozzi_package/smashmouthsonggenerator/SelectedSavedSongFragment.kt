package com.perozzi_package.smashmouthsonggenerator

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSelectedSavedSongBinding
import com.perozzi_package.smashmouthsonggenerator.ui.saved_songs.SavedSongsViewModel

class SelectedSavedSongFragment : Fragment() {

    private val args: SelectedSavedSongFragmentArgs by navArgs()
    private lateinit var binding: FragmentSelectedSavedSongBinding
    private lateinit var ssViewModel: SavedSongsViewModel
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSelectedSavedSongBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        ssViewModel = SavedSongsViewModel(requireActivity().application)

        binding.selectedSongLyrics.text = args.savedItemLyrics
        binding.selectedSongTitle.text = args.savedItemTitle

        // binding.deleteButton.setOnClickListener { deleteUser() }

    }
}