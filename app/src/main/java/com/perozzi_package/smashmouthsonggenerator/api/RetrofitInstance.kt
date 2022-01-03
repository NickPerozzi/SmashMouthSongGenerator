package com.perozzi_package.smashmouthsonggenerator.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    // TODO(.baseUrl for @GET request is "https://kst0gmvxif.execute-api.us-east-2.amazonaws.com"
    val api: LyricsApi by lazy {
        Retrofit.Builder()
            .baseUrl("https://6w10in8jdk.execute-api.us-east-2.amazonaws.com")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(LyricsApi::class.java)
    }

}