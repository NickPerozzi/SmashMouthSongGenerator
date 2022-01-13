package com.perozzi_package.smashmouthsonggenerator.data

import android.util.Log
import com.perozzi_package.smashmouthsonggenerator.api.RetrofitInstance
import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.Lyrics
import retrofit2.HttpException
import java.io.IOException

class GeneratedLyricsRepository {

    suspend fun getGeneratedLyrics(albumWeightsMap: MutableMap<String, Int>): Lyrics? {
        val response = try {
            RetrofitInstance.api.getLyrics(
                albumWeightsMap
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