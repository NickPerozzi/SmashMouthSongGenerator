package com.perozzi_package.smashmouthsonggenerator.api

import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.Lyrics
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface LyricsApi {

    @POST("/onepointone/generatedlyrics")
    suspend fun getLyrics(
        @Body albumWeightDict: Map<String, Int>
    ): Response<Lyrics>

}
