package com.example.footballapidemo.news

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel

class NewsViewModel: ViewModel() {
    private val _newsList = mutableStateListOf<News>()
    val newsList: SnapshotStateList<News> = _newsList
    fun addNews(news: News){
        _newsList.add(news)
    }
}