package com.perozzi_package.smashmouthsonggenerator.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface SavedSongDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addSavedSong(savedSong: SavedSong)

    @Query("SELECT * FROM saved_songs_table ORDER BY id ASC")
    fun readAllData(): LiveData<List<SavedSong>>

}