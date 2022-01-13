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


/*
    @GET("/test/transactions")
    suspend fun getLyrics(
        @Query("fymW") fymW: String,
        @Query("alW") alW: String,
        @Query("smW") smW: String,
        @Query("gtpW") gtpW: String,
        @Query("asshW") asshW: String,
        @Query("tgorW") tgorW: String,
        @Query("sgW") sgW: String,
        @Query("mW") mW: String,
    ): Response<Lyrics>
*/

}
