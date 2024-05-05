package com.example.footballapidemo.news

import com.example.footballapidemo.news.News

data class Result(
    val allnum: Int,  //返回的news总数
    val curpage: Int,  //正在访问的页面
    val newslist: List<News>  //实际的新闻列表
)