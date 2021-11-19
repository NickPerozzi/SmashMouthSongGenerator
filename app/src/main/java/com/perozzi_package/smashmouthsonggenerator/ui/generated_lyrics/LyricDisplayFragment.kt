package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.LyricAdapter
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentLyricDisplayBinding

class LyricDisplayFragment : Fragment() {

    private lateinit var navController: NavController
    lateinit var binding: FragmentLyricDisplayBinding
    private lateinit var ldViewModel: LyricDisplayViewModel
    private lateinit var lyricListAdapter: LyricAdapter


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLyricDisplayBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        /*val navHostFragment = requireActivity().supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container_view) as NavHostFragment
        navController = navHostFragment.navController*/
        //
        // navController = Navigation.findNavController(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        ldViewModel = LyricDisplayViewModel(requireActivity().application)

        val lyricView = binding.lyricDisplayTextView
        ldViewModel.lyrics.observe(viewLifecycleOwner, {
            lyricView.text = it
        })

//        val recyclerView: RecyclerView = binding.lyricDisplayRecyclerView
//        recyclerView.layoutManager = ldViewModel.lyricGridLayoutManager
//        recyclerView.setHasFixedSize(true)

//        lyricListAdapter = LyricAdapter()
//        recyclerView.adapter = lyricListAdapter
//        lyricListAdapter.submitList(ldViewModel.putLyricsIntoList())

    }
}