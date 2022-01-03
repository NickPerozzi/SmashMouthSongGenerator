package com.perozzi_package.smashmouthsonggenerator.api

import com.perozzi_package.smashmouthsonggenerator.ui.generated_lyrics.Lyrics
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

import retrofit2.http.Body
import retrofit2.http.POST

interface LyricsApi {
//        TODO(Spent >3 hours looking into syntax on how return values from @POST in AWS, to no avail.
//         A new lambda and API gateway exists for @POST, and it works, but it doesn't return lyrics.
//         For now I will be using the original GET, but I will come back to this eventually.)
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

// I'm not sending the string/url
// I'm sending a body of stuff TO the string/url

// NEW method (1.1)
// Body has the information I'm passing
// ("Post" does not return any data, but I need it to return "String")
