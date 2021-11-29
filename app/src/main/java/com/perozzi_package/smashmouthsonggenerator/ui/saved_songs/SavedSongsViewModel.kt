package com.perozzi_package.smashmouthsonggenerator.ui.saved_songs

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.adapters.SavedSongAdapter
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongDatabase
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SavedSongsViewModel(application: Application) : AndroidViewModel(application) {

    val readAllData: LiveData<List<SavedSong>>
    private val repository: SavedSongRepository

    init {
        val savedSongDao = SavedSongDatabase.getDatabase(application).savedSongDao()
        repository = SavedSongRepository(savedSongDao)
        readAllData = repository.readAllData
    }

    private var savedSongsGridLayoutManager: GridLayoutManager? = GridLayoutManager(
        application, 2, LinearLayoutManager.VERTICAL, false)

    fun addSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSavedSong(savedSong)
        }
    }

    fun deleteSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavedSong(savedSong)
        }
    }

    fun prepareSavedSongsRecyclerView(
        thisInterface: SavedSongAdapter.OnClickDeleteInterface,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = savedSongsGridLayoutManager
        recyclerView.setHasFixedSize(true)
        val adapter = SavedSongAdapter(thisInterface)
        recyclerView.adapter = adapter
    }

}