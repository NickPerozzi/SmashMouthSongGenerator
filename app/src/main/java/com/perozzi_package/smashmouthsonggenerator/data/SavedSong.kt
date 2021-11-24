package com.perozzi_package.smashmouthsonggenerator.data

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "saved_songs_table")
data class SavedSong(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var songTitle: String,
    var songLyrics: String
): Parcelable