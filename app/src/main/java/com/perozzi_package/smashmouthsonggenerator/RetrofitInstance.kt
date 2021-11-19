package com.perozzi_package.smashmouthsonggenerator

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {

    val api: LyricsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://kst0gmvxif.execute-api.us-east-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LyricsApi::class.java)
    }

}