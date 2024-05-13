package com.example.footballapidemo.data

data class StandingsJson(
    val area: Area,
    val competition: Competition,
    val filters: Filters,
    val season: Season,
    val standings: List<Standing>
)