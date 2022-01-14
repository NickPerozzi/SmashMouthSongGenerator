package com.perozzi_package.smashmouthsonggenerator.ui.saved_songs

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.adapters.SavedSongAdapter
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentSavedSongsBinding
import org.koin.androidx.viewmodel.ext.android.viewModel

class SavedSongsFragment : Fragment(), SavedSongAdapter.OnClickDeleteInterface {

    private val ssViewModel: SavedSongsViewModel by viewModel()

    private lateinit var binding: FragmentSavedSongsBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentSavedSongsBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val ssAdapter = SavedSongAdapter(this as SavedSongAdapter.OnClickDeleteInterface)
        ssViewModel.prepareSavedSongsRecyclerView(this, binding.savedSongsRecyclerView)
        binding.savedSongsViewModel = ssViewModel
        binding.savedSongsRecyclerView.adapter = ssAdapter
        ssViewModel.readAllData.observe(viewLifecycleOwner, {
            ssAdapter.submitList(it)
        })

    }

    private fun deleteUser(savedSong: SavedSong) {
        val builder = androidx.appcompat.app.AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Yeah") { _, _ ->
            ssViewModel.deleteSavedSong(savedSong)
            Toast.makeText(
                requireContext(),
                "\"${savedSong.songTitle}\" was deleted",
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setNegativeButton("Nah") { _, _ ->
            Toast.makeText(
                requireContext(),
                getString(R.string.heck_yeah),
                Toast.LENGTH_SHORT
            ).show()
        }
        builder.setTitle(getString(R.string.delete_song_prompt,savedSong.songTitle))
        builder.setMessage(getString(R.string.you_sure_you_want_to_delete))
        builder.create().show()
    }

    override fun onClickDelete(savedSong: SavedSong) {
        deleteUser(savedSong)
    }

}