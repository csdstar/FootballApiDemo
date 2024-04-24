package com.example.footballapidemo.data

data class Teams(
    val filters: Filters,
    val teams: List<Team>,
    val resultSet: ResultSet
)
