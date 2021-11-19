package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.perozzi_package.smashmouthsonggenerator.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.LyricAdapter
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.RetrofitInstance
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentWeightAssignmentBinding
import com.perozzi_package.smashmouthsonggenerator.ui.MainActivity
import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.LyricDisplayViewModel
import retrofit2.HttpException
import java.io.IOException

class WeightAssignmentFragment : Fragment(), AlbumGridAdapter.OnSeekBarChangeListenerInterface {

    private lateinit var lyricAdapter: LyricAdapter

    private lateinit var navController: NavController
    private lateinit var binding: FragmentWeightAssignmentBinding
    private lateinit var waViewModel: WeightAssignmentViewModel
    private lateinit var ldViewModel: LyricDisplayViewModel
    private lateinit var albumListAdapter: AlbumGridAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightAssignmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = this
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        lyricAdapter = LyricAdapter()
        navController = Navigation.findNavController(view)
        (requireActivity() as AppCompatActivity).supportActionBar?.hide()
        ldViewModel = LyricDisplayViewModel(requireActivity().application)
        waViewModel = WeightAssignmentViewModel(requireActivity().application)
        waViewModel.prepareDataForAdapter()
        val recyclerView: RecyclerView = binding.albumRecyclerView
        recyclerView.layoutManager = waViewModel.weightGridLayoutManager
        recyclerView.setHasFixedSize(true)
        albumListAdapter =  AlbumGridAdapter(this)
        recyclerView.adapter = albumListAdapter
        albumListAdapter.submitList(waViewModel.arrayForAlbumGrid)

        val loadingIconLayout = binding.loadingIconConstraintLayout
        val loadingIcon = binding.loadingIcon
        Glide.with(this).load(R.drawable.somebody_gif_loading).into(loadingIcon)
        val button = binding.generateLyricsButton
        button.setOnClickListener {
            lifecycleScope.launchWhenCreated {
                loadingIconLayout.isVisible = true
                val response = try {
                    RetrofitInstance.api.getLyrics(
                        waViewModel.albumWeights[0].toString(),
                        waViewModel.albumWeights[1].toString(),
                        waViewModel.albumWeights[2].toString(),
                        waViewModel.albumWeights[3].toString(),
                        waViewModel.albumWeights[4].toString(),
                        waViewModel.albumWeights[5].toString(),
                        waViewModel.albumWeights[6].toString(),
                        waViewModel.albumWeights[7].toString()
                    )
                } catch (e: IOException) {
                    Log.e("WeightFragment", "IOException, you might not have internet connection")
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e("WeightFragment", "HttpException, unexpected response")
                    return@launchWhenCreated
                }
                if (response.isSuccessful && response.body() != null) {
                    lyricAdapter.listOfLyrics = listOf(response.body()!!)
                    ldViewModel.lyrics.value = response.body()!!.lyrics

                    navController.navigate(R.id.action_weightAssignmentFragment_to_lyricDisplayFragment)
                } else {
                    Log.e("WeightFragment", "Response not successful")
                }
                loadingIconLayout.isVisible = false
            }
        }


    }

    override fun onSeekBarChange(position: Int, weight: Int, textView: TextView) {
        waViewModel.albumWeights[position] = weight
        textView.text = resources.getString(R.string.album_weight_xxx, weight.toString())
        // listAdapter.notifyItemChanged(position)

    }

}