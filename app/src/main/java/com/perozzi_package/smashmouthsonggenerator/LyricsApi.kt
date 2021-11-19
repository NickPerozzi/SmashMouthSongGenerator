package com.perozzi_package.smashmouthsonggenerator

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface LyricsApi {

    @GET("/test/transactions")
    suspend fun getLyrics(@Query("fymW") fymW: String, @Query("alW") alW: String,
                  @Query("smW") smW: String, @Query("gtpW") gtpW: String,
                  @Query("asshW") asshW: String, @Query("tgorW") tgorW: String,
                  @Query("sgW") sgW: String, @Query("mW") mW: String,
    ): Response<Lyrics>

}