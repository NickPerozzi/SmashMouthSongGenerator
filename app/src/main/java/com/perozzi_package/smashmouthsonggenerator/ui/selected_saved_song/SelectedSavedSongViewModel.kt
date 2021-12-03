package com.perozzi_package.smashmouthsonggenerator.ui.selected_saved_song

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongDatabase
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class SelectedSavedSongViewModel(application: Application) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<SavedSong>>
    private val repository: SavedSongRepository

    init {
        val savedSongDao = SavedSongDatabase.getDatabase(application).savedSongDao()
        repository = SavedSongRepository(savedSongDao)
        readAllData = repository.readAllData
    }

    fun updateSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.updateSavedSong(savedSong)
        }
    }

}