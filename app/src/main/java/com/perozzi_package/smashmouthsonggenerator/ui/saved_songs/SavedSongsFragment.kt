package com.perozzi_package.smashmouthsonggenerator.ui.saved_songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import com.perozzi_package.smashmouthsonggenerator.SavedSongAdapter
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSavedSongsBinding

class SavedSongsFragment : Fragment(), SavedSongAdapter.OnClickDeleteInterface {

    private lateinit var ssViewModel: SavedSongsViewModel
    private lateinit var binding: FragmentSavedSongsBinding
    private lateinit var savedSong: SavedSong
    private val args: SavedSongsFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedSongsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        ssViewModel = SavedSongsViewModel(requireActivity().application)

        val savedLyrics = args.lyricsToSave
        val savedTitle = args.songTitleToSave
        savedSong = SavedSong(0, savedTitle, savedLyrics)

        insertDataToDatabase()
        val ssAdapter = SavedSongAdapter(this as SavedSongAdapter.OnClickDeleteInterface)
        ssViewModel.prepareSavedSongsRecyclerView(this,binding.savedSongsRecyclerView)
        binding.savedSongsRecyclerView.adapter = ssAdapter
        ssViewModel.readAllData.observe(viewLifecycleOwner, {
            ssAdapter.submitList(it)
        })
    }

    private fun insertDataToDatabase() {
        ssViewModel.addSavedSong(savedSong)
        Toast.makeText(requireContext(), "Song saved!", Toast.LENGTH_SHORT).show()
    }

    private fun deleteUser(savedSong: SavedSong) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yeah") { _, _ ->
            ssViewModel.deleteSavedSong(savedSong)
            Toast.makeText(
                requireContext(),
                "\"${savedSong.songTitle}\" was deleted",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Keep it!") { _, _ ->
            Toast.makeText(
                requireContext(),
                "Heck yeah",
                Toast.LENGTH_SHORT).show()
        }
        builder.setTitle("Delete \"${savedSong.songTitle}\"?")
        builder.setMessage("You sure you want to delete this absolute banger?")
        builder.create().show()
    }

    override fun onClickDelete(savedSong: SavedSong) {
        deleteUser(savedSong)
    }

}