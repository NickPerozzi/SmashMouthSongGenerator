package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentLyricDisplayBinding

class LyricDisplayFragment : Fragment() {

    private lateinit var ldViewModel: LyricDisplayViewModel
    private lateinit var navController: NavController
    private lateinit var binding: FragmentLyricDisplayBinding
    private val args: LyricDisplayFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLyricDisplayBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        ldViewModel = LyricDisplayViewModel(requireActivity().application)
        navController = Navigation.findNavController(view)

        val customTitle = binding.songTitleEditText
        val lyricView = binding.lyricDisplayTextView
        val latestLyrics = args.myPassedLyricsInFragment
        lyricView.text = latestLyrics

        val saveLocallyButton = binding.saveLocallyButton
        saveLocallyButton.setOnClickListener {
            val songTitle = customTitle.text.toString()
            if (songTitle.isEmpty()) {
                Toast.makeText(
                    context, "Please give your song a name", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            if (latestLyrics == "Your generated lyrics will go here!") {
                Toast.makeText(
                    context, "You have to generate a song first!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val action = LyricDisplayFragmentDirections
                .actionLyricDisplayFragmentToSavedSongsFragment(latestLyrics, songTitle, true)
            navController.navigate(action)
        }

        binding.generateAgainButton.setOnClickListener { navController.popBackStack() }
    }
}