package com.example.footballapidemo.data

data class AwayTeam(
    val draws: Int,
    val id: Int,
    val losses: Int,
    val name: String,
    val wins: Int
)
//这里的是在head2head中的统计值