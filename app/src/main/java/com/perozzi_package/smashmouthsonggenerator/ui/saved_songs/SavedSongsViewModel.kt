package com.perozzi_package.smashmouthsonggenerator.ui.saved_songs

import android.app.Application
import android.view.View
import androidx.lifecycle.*
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.perozzi_package.smashmouthsonggenerator.adapters.SavedSongAdapter
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongDatabase
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class SavedSongsViewModel(private val app: Application) : AndroidViewModel(app) {

    val readAllData: LiveData<List<SavedSong>>
    private val repository: SavedSongRepository

    init {
        val savedSongDao = SavedSongDatabase.getDatabase(app).savedSongDao()
        repository = SavedSongRepository(savedSongDao)
        readAllData = repository.readAllData
    }

    var savedSongsGoHereTextVisibility: MutableLiveData<Int> = readAllData.map{
        if (readAllData.value?.size == 0) {
            View.VISIBLE
        } else {
            View.GONE
        }
    } as MutableLiveData<Int>

    fun deleteSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.deleteSavedSong(savedSong)
        }
    }

    fun prepareSavedSongsRecyclerView(
        thisInterface: SavedSongAdapter.OnClickDeleteInterface,
        recyclerView: RecyclerView
    ) {
        recyclerView.layoutManager = GridLayoutManager(
            app, 1, LinearLayoutManager.VERTICAL, false
        )
        recyclerView.setHasFixedSize(true)
        val adapter = SavedSongAdapter(thisInterface)
        recyclerView.adapter = adapter
    }

}