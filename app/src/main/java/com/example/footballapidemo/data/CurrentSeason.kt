package com.example.footballapidemo.data

data class CurrentSeason(
    val currentMatchday: Int,
    val endDate: String,
    val id: Int,
    val startDate: String,
    val winner: Any
)