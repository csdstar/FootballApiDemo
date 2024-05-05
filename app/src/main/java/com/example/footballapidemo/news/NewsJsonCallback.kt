package com.example.footballapidemo.news

import android.util.Log
import com.example.basic_ui_demo.news.NewsJson
import com.example.footballapidemo.TAG

fun newsJsonCallback(newsJson: NewsJson, viewModel: NewsViewModel){
    Log.d(TAG, newsJson.msg)
    val newsList = newsJson.result?.newslist
    if (newsList != null){
        for(news in newsList){
            viewModel.addNews(news)
            Log.d("MyTag", news.title)
        }
    }
    else{
        Log.e(TAG, "no newsList")
    }
}