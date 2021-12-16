package com.perozzi_package.smashmouthsonggenerator.api

import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.Lyrics
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Query

interface LyricsApi {

    @GET("/test/transactions")
    suspend fun getLyrics(

        // NEW method (1.1)
        // Body has the information I'm passing
        // ("Get" in AWS does not support body, looking into "Post")
        @Body albumWeightDict: Map<String, Int>

        // OLD method (1.0)
        // Address has the information I'm passing
        // Empty body, all parameters passed through address string
//        @Query("fymW") fymW: String, @Query("alW") alW: String,
//        @Query("smW") smW: String, @Query("gtpW") gtpW: String,
//        @Query("asshW") asshW: String, @Query("tgorW") tgorW: String,
//        @Query("sgW") sgW: String, @Query("mW") mW: String,

        ): Response<Lyrics>

}

// I'm not sending the string/url
// I'm sending a body of stuff TO the string/url