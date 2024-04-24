package com.example.news_screen_demo.news

import com.example.basic_ui_demo.news.NewsJson
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsInterface {
    @GET
    suspend fun getNews(
        @Query("rand") rand: Int = 0,
        @Query("num") num: Int = 20
    ): Response<NewsJson>
}