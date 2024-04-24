package com.example.basic_ui_demo.news

data class News(
    val ctime: String,  //新闻时间
    val description: String,  //新闻描述
    val id: String,    //新闻的id
    val picUrl: String,  //外显的图片的url
    val source: String,  //新闻来源
    val title: String,  //标题
    val url: String  //原文网址
)