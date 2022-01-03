package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.app.Application
import android.view.View
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.adapters.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.data.GeneratedLyricsRepository
import kotlinx.coroutines.launch
import java.util.*

class WeightAssignmentViewModel(application: Application) :
    AndroidViewModel(application) {

    private val repository: GeneratedLyricsRepository = GeneratedLyricsRepository()

    // TODO(
    //  DataStore in viewModel good practice,
    //  context in viewModel bad practice,
    //  DataStore needs context
    //  )
    private val dataStore: DataStore<Preferences> =
        getApplication<Application>().applicationContext.createDataStore(name = "settings")

    private var weightGridLayoutManager: GridLayoutManager? =
        GridLayoutManager(application, 1, LinearLayoutManager.VERTICAL, false)

    // Eventually, this data will not be needed here since it will be scraped from elsewhere.
    // The layout of this Smash Mouth data is a sample for how all artists' data will look, once
    // the functionality is implemented (images will be grabbed via url)
    // TODO(For loop that does this guy JSONObject(smashMouthDictionary["fush_yu_mang"]).toString()
    private val bandDiscography: MutableMap<String, Map<String, Any>> = mutableMapOf(
        "fush_yu_mang" to mapOf(
            "name" to "Fush Yu Mang",
            "year" to "(1997)",
            "imageAddress" to R.drawable.alb_1_fush_yu_mang_97
        ),
        "astro_lounge" to mapOf(
            "name" to "Astro Lounge",
            "year" to "(1999)",
            "imageAddress" to R.drawable.alb_2_astro_lounge_99
        ),
        "smash_mouth" to mapOf(
            "name" to "Smash Mouth",
            "year" to "(2001)",
            "imageAddress" to R.drawable.alb_3_smash_mouth_01
        ),
        "get_the_picture" to mapOf(
            "name" to "Get the Picture",
            "year" to "(2003)",
            "imageAddress" to R.drawable.alb_4_get_the_picture_03
        ),
        "all_star_smash_hits" to mapOf(
            "name" to "All Star Smash Hits",
            "year" to "(2005)",
            "imageAddress" to R.drawable.alb_5_all_star_smash_hits_05
        ),
        "the_gift_of_rock" to mapOf(
            "name" to "The Gift of Rock",
            "year" to "(2005)",
            "imageAddress" to R.drawable.alb_6_the_gift_of_rock_05
        ),
        "summer_girl" to mapOf(
            "name" to "Summer Girl",
            "year" to "(2006)",
            "imageAddress" to R.drawable.alb_7_summer_girl_06
        ),
        "magic" to mapOf(
            "name" to "Magic",
            "year" to "(2012)",
            "imageAddress" to R.drawable.alb_8_magic_12
        )
    )

    var lyricGenerationStatus: MutableLiveData<String> = MutableLiveData("Before")


    var loadingIconVisibility: MutableLiveData<Int> = lyricGenerationStatus.map {
        if (it == "During") {
            View.VISIBLE
        } else {
            View.GONE
        }
    } as MutableLiveData<Int>

    fun prepareDataForAdapter(): ArrayList<AlbumGrid> {
        val arrayForAlbumGrid: ArrayList<AlbumGrid> = arrayListOf()
        val yearsList = mutableListOf<String>()
        val titlesList = mutableListOf<String>()
        val imageAddressList = mutableListOf<Int>()
        val albumWeightsMapValues = ArrayList(albumWeightsMap.values)
        for (album in bandDiscography.keys) {
            yearsList.add(bandDiscography[album]!!["year"] as String)
            titlesList.add(bandDiscography[album]!!["name"] as String)
            imageAddressList.add(bandDiscography[album]!!["imageAddress"] as Int)
        }
        for (index in 0 until yearsList.size) {
            arrayForAlbumGrid.add(
                AlbumGrid(
                    titlesList[index],
                    yearsList[index],
                    imageAddressList[index],
                    albumWeightsMapValues[index]
                )
            )
        }
        return arrayForAlbumGrid
    }
    private fun createAlbumWeightsMap(): MutableMap<String,Int> {
        val tempKeys: MutableList<String> = mutableListOf()
        val tempValues: MutableList<Int> = mutableListOf()
        for (album in bandDiscography.keys) {
            bandDiscography[album]?.let{ tempKeys.add(it["name"] as String) }
            tempValues.add(1)
        }
        return tempKeys.zip(tempValues).toMap().toMutableMap()
    }
    val albumWeightsMap = createAlbumWeightsMap()

    fun prepareAlbumRecyclerView(
        thisInterface: AlbumGridAdapter.OnSeekBarChangeListenerInterface,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = weightGridLayoutManager
        recyclerView.setHasFixedSize(true)
        val adapter = AlbumGridAdapter(thisInterface)
        recyclerView.adapter = adapter
        adapter.submitList(prepareDataForAdapter())
    }

    fun areThereAnyNonZeroWeights(): Boolean =
        albumWeightsMap.values.count { it == 0 } != albumWeightsMap.size

    fun retrieveLyricsFromAPI() {
        viewModelScope.launch {
            lyricGenerationStatus.value = "During"
            val generatedLyrics = repository.getGeneratedLyrics(albumWeightsMap)
            generatedLyrics?.let { save("recently generated lyrics", it.lyrics ?: "henlo :)") }
            lyricGenerationStatus.value = "After"
        }
    }

    private suspend fun save(key: String, value: String) {
        val dataStoreKey = preferencesKey<String>(key)
        dataStore.edit { settings ->
            settings[dataStoreKey] = value
        }
    }
}