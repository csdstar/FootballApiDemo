package com.example.footballapidemo.data

data class ResultSet(
    val competitions: String,
    val count: Int,
    val draws: Int,
    val first: String,
    val last: String,
    val losses: Int,
    val played: Int,
    val wins: Int
)