package com.example.footballapidemo.data

data class Score(
    val duration: String,
    val fullTime: Int,
    val halfTime: HalfTime,
    val winner: Any
)