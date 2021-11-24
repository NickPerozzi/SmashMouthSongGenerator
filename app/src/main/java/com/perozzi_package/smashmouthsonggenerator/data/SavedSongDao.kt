package com.perozzi_package.smashmouthsonggenerator.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SavedSongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSavedSong(savedSong: SavedSong)

    @Delete
    suspend fun deleteSavedSong(savedSong: SavedSong)

    @Query("SELECT * FROM saved_songs_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<SavedSong>>

}