package com.example.footballapidemo.data

data class MatchesJson(
    val filters: Filters,
    val matches: List<Match>,
    val resultSet: ResultSet,
    val competition: Competition
)