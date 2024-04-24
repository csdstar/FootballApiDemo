package com.example.footballapidemo.data

data class Anys<T>(
    val filters: Filters,
    val anys: List<T>,
    val resultSet: ResultSet
)