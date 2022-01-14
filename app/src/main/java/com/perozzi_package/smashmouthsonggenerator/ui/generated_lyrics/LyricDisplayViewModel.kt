package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.app.Application
import android.widget.Toast
import androidx.datastore.preferences.core.preferencesKey
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.perozzi_package.smashmouthsonggenerator.R
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongDatabase
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LyricDisplayViewModel(application: Application/*, val repo: SavedSongRepository*/) : AndroidViewModel(application) {

    private val readAllData: LiveData<List<SavedSong>>
    private val repository: SavedSongRepository

    init {
        val savedSongDao = SavedSongDatabase.getDatabase(application).savedSongDao()
        repository = SavedSongRepository(savedSongDao)
        readAllData = repository.readAllData
    }

    fun insertDataToDatabase(songToSave: SavedSong) {
        addSavedSong(songToSave)
        addToDatabaseStatus.value = true
    }

    val addToDatabaseStatus: MutableLiveData<Boolean> = MutableLiveData(false)


    fun addSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) {
            repository.addSavedSong(savedSong)
        }
    }

}