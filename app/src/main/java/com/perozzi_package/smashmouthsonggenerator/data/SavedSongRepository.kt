package com.perozzi_package.smashmouthsonggenerator.data

import androidx.lifecycle.LiveData

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
