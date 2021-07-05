package com.vincent.adidassample.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

object RetrofitBuilder {
    const val BASE_URL: String = "http://api.tianapi.com/nba/"

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val adidasService: AdidasService = retrofit.create()
}
