package com.vincent.adidassample.repository

import com.vincent.adidassample.api.APIHelper

class MainRepository(private val apiHelper: APIHelper) {

    suspend fun getNews(page_count: Int) = apiHelper.getNews("ed1ea8316bd06883015572fe1f36ebd5", 20, page_count)
}