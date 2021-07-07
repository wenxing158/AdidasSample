package com.vincent.adidassample.model

data class NewsListResponse(
    val code: Int,
    val msg: String,
    val newslist: MutableList<NewsItem>
)