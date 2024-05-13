package com.example.footballapidemo.data

data class Aggregates(
    val awayTeam: AwayTeam,
    val homeTeam: HomeTeam,
    val numberOfMatches: Int,
    val totalGoals: Int
)

//统计量