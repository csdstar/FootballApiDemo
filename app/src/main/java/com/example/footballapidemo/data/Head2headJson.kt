package com.example.footballapidemo.data

data class Head2headJson(
    val aggregates: Aggregates,
    val filters: Filters,
    val matches: List<Match>,
    val resultSet: ResultSet
)