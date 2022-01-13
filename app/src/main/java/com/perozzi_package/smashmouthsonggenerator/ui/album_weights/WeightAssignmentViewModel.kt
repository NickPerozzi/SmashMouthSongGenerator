package com.perozzi_package.smashmouthsonggenerator.ui.album_weights

import android.app.Application
import android.view.View
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.adapters.AlbumGridAdapter
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.data.DiscographyRepository
import com.perozzi_package.smashmouthsonggenerator.data.GeneratedLyricsRepository
import com.perozzi_package.smashmouthsonggenerator.koin.ImportantClass
import kotlinx.coroutines.launch
import java.util.*

class WeightAssignmentViewModel(
    application: Application,
    importantClass: ImportantClass,
    discographyRepository: DiscographyRepository,
    private val generatedLyricsRepository: GeneratedLyricsRepository
) :
    ViewModel() {

    private val artistDiscography = discographyRepository.smashMouthDiscography

    var lyricGenerationStatus: MutableLiveData<String> = MutableLiveData("Before")

    // Learning Dependency Injection, this doesn't have any practical use
    init {
        importantClass.createImportantString()
    }


    private val dataStore: DataStore<Preferences> = application.createDataStore(name = "settings")


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
        for (album in artistDiscography.keys) {
            yearsList.add(artistDiscography[album]!!["year"] as String)
            titlesList.add(artistDiscography[album]!!["name"] as String)
            imageAddressList.add(artistDiscography[album]!!["imageAddress"] as Int)
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
        for (album in artistDiscography.keys) {
            artistDiscography[album]?.let { tempKeys.add(it["name"] as String) }
            tempValues.add(1)
        }
        return tempKeys.zip(tempValues).toMap().toMutableMap()
    }

    val albumWeightsMap = createAlbumWeightsMap()

    fun prepareAlbumRecyclerView(
        thisInterface: AlbumGridAdapter.OnSeekBarChangeListenerInterface,
        recyclerView: RecyclerView,
        app: Application
    ) {
        recyclerView.layoutManager = GridLayoutManager(app, 1, LinearLayoutManager.VERTICAL, false)
        recyclerView.setHasFixedSize(true)
        val adapter = AlbumGridAdapter(thisInterface)
        recyclerView.adapter = adapter
        adapter.submitList(prepareDataForAdapter())
    }

    fun areThereAnyNonZeroWeights(): Boolean =
        albumWeightsMap.values.count { it == 0 } != albumWeightsMap.size

    fun retrieveLyrics() {
        viewModelScope.launch {
            lyricGenerationStatus.value = "During"
            val generatedLyrics = generatedLyricsRepository.getGeneratedLyrics(albumWeightsMap)
            generatedLyrics?.let { save("recently generated lyrics", it.lyrics) }
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