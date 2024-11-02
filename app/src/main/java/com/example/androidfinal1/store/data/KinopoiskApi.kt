package com.example.androidfinal1.store.data

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object KinopoiskApi {
    private const val BASE_URL = "https://kinopoiskapiunofficial.tech/api/"

    val retrofitService: KinopoiskApiService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(KinopoiskApiService::class.java)
    }
}