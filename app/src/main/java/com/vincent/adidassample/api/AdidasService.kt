package com.vincent.adidassample.api

import com.vincent.adidassample.model.NewsListResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface AdidasService {

    @GET("index")
    suspend fun getNews(@Query("key") key: String, @Query("num") num: Int, @Query("page") page: Int): NewsListResponse
}