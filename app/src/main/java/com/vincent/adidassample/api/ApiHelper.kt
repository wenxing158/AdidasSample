package com.vincent.adidassample.api

class APIHelper(private val apiService: AdidasService) {

    suspend fun getNews(key: String, num: Int, page: Int) = apiService.getNews(key, num, page)
}