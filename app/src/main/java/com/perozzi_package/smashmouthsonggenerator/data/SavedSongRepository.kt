package com.perozzi_package.smashmouthsonggenerator.data

import android.util.Log
import androidx.lifecycle.LiveData
import com.perozzi_package.smashmouthsonggenerator.api.RetrofitInstance
import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.Lyrics
import retrofit2.HttpException
import java.io.IOException

class SavedSongRepository(private val savedSongDao: SavedSongDao) {

    val readAllData: LiveData<List<SavedSong>> = savedSongDao.readAllData()

    suspend fun addSavedSong(savedSong: SavedSong) {
        savedSongDao.addSavedSong(savedSong)
    }

    suspend fun deleteSavedSong(savedSong: SavedSong) {
        savedSongDao.deleteSavedSong(savedSong)
    }

    suspend fun updateSavedSong(savedSong: SavedSong) {
        savedSongDao.updateSavedSong(savedSong)
    }
}
