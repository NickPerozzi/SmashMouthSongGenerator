package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.bumptech.glide.Glide
import com.perozzi_package.smashmouthsonggenerator.*
import com.perozzi_package.smashmouthsonggenerator.adapters.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.api.RetrofitInstance
import com.perozzi_package.smashmouthsonggenerator.databinding.FragmentWeightAssignmentBinding
import retrofit2.HttpException
import java.io.IOException

class WeightAssignmentFragment : Fragment(), AlbumGridAdapter.OnSeekBarChangeListenerInterface {

    private lateinit var navController: NavController
    private lateinit var binding: FragmentWeightAssignmentBinding
    private lateinit var waViewModel: WeightAssignmentViewModel
    private lateinit var dataStore: DataStore<Preferences>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentWeightAssignmentBinding.inflate(layoutInflater)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataStore = requireContext().createDataStore(name = "settings")

        navController = Navigation.findNavController(view)

        waViewModel = WeightAssignmentViewModel(requireActivity().application)

        waViewModel.prepareDataForAdapter()
        waViewModel.prepareAlbumRecyclerView(this, binding.albumRecyclerView)

        Glide.with(this).load(R.drawable.somebody_gif_loading).into(binding.loadingIcon)
        val loadingIconLayout = binding.loadingIconConstraintLayout

        binding.generateLyricsButton.setOnClickListener {
            if (!waViewModel.areThereAnyNonZeroWeights()) {
                Toast.makeText(
                    context, resources.getString(R.string.you_need_some_weight),
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }
            waViewModel.calibrateRedundantWeighting()
            val weights = waViewModel.albumWeights
            lifecycleScope.launchWhenCreated {
                loadingIconLayout.isVisible = true
                val response = try {
                    RetrofitInstance.api.getLyrics(
                        weights[0], weights[1], weights[2], weights[3],
                        weights[4], weights[5], weights[6], weights[7]
                    )
                } catch (e: IOException) {
                    Log.e(resources.getString(R.string.weight_fragment), resources.getString(R.string.io_error))
                    return@launchWhenCreated
                } catch (e: HttpException) {
                    Log.e(resources.getString(R.string.weight_fragment), resources.getString(R.string.http_error))
                    return@launchWhenCreated
                }
                if (response.isSuccessful && response.body() != null) {
                    save(getString(R.string.recently_generated_lyrics), response.body()!!.lyrics)
                    try {
                        navController.navigate(
                            R.id.action_weightAssignmentFragment_to_lyricDisplayFragment
                        )
                    } catch (e:IllegalArgumentException) {
                        Toast.makeText(
                            context, resources.getString(R.string.your_lyrics_are_ready),
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                } else {
                    Log.e(getString(R.string.weight_fragment), getString(R.string.response_not_successful))
                }
                loadingIconLayout.isVisible = false
            }
        }
    }

    override fun onSeekBarChange(position: Int, weight: Int, textView: TextView) {
        waViewModel.albumWeights[position] = weight.toString()
        textView.text = resources.getString(R.string.album_weight_xxx, weight.toString())
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
}