package com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics

import android.app.Application
import androidx.datastore.preferences.core.preferencesKey
import androidx.datastore.preferences.createDataStore
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.perozzi_package.smashmouthsonggenerator.data.SavedSong
import com.perozzi_package.smashmouthsonggenerator.data.SavedSongRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class LyricDisplayViewModel(
    application: Application,
    private val repository: SavedSongRepository
): AndroidViewModel(application) {

    val savedToDatabaseIndicator: MutableLiveData<Boolean> = MutableLiveData(false)

    fun insertDataToDatabase(songToSave: SavedSong) {
        addSavedSong(songToSave)
        savedToDatabaseIndicator.value = true
    }

    private fun addSavedSong(savedSong: SavedSong) {
        viewModelScope.launch(Dispatchers.IO) { repository.addSavedSong(savedSong) }
    }

    suspend fun readFromDataStore(key: String): String? {
        val dataStoreKey = preferencesKey<String>(key)
        val dataStore =
            getApplication<Application>().applicationContext.createDataStore(name = "settings")
        val preferences = dataStore.data.first()
        return preferences[dataStoreKey]
    }

}