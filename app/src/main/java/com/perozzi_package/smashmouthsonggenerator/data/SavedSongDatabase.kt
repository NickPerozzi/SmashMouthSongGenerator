package com.perozzi_package.smashmouthsonggenerator.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [SavedSong::class], version = 1, exportSchema = false)
abstract class SavedSongDatabase: RoomDatabase() {

    abstract fun savedSongDao() : SavedSongDao

    companion object {
        @Volatile // means that rights to this field are made visible to other threads
        private var INSTANCE: SavedSongDatabase? = null

        fun getDatabase(context: Context): SavedSongDatabase{
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    SavedSongDatabase::class.java,
                    "saved_song_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }
    }

}