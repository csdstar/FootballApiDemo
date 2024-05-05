package com.example.basic_ui_demo.news

import com.example.footballapidemo.news.Result

data class NewsJson(
    val code: Int,
    val msg: String,
    val result: Result?
)