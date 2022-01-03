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

class GeneratedLyricsRepository {

    suspend fun getGeneratedLyrics(albumWeightsMap: MutableMap<String, Int>): Lyrics? {
//        val albumWeightsMapValues = albumWeightsMap.values.toList()
        val response = try {
            RetrofitInstance.api.getLyrics(
                albumWeightsMap
//                albumWeightsMapValues[0].toString(),
//                albumWeightsMapValues[1].toString(),
//                albumWeightsMapValues[2].toString(),
//                albumWeightsMapValues[3].toString(),
//                albumWeightsMapValues[4].toString(),
//                albumWeightsMapValues[5].toString(),
//                albumWeightsMapValues[6].toString(),
//                albumWeightsMapValues[7].toString()
            )
        } catch (e: IOException) {
            Log.e(
                "WeightFragment",
                "IOException, you might not have internet connection"
            )
            return null
        } catch (e: HttpException) {
            Log.e(
                "WeightFragment",
                "HttpException, unexpected response"
            )
            return null
        }
        if (response.isSuccessful && response.body() != null) {
            return response.body()
        }
        return null
    }
}