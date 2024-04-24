package com.example.basic_ui_demo.news

data class Result(
    val allnum: Int,  //返回的news总数
    val curpage: Int,  //正在访问的页面
    val newsList: List<News>  //实际的新闻列表
)