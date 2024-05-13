package com.example.footballapidemo.data

data class ScorersJson(
    val competition: Competition,
    val count: Int,
    val filters: Filters,
    val season: Season,
    val scorers: List<Scorer>
)