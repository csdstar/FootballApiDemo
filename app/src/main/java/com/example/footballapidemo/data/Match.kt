package com.example.footballapidemo.data

data class Match(
    val area: Area?,
    val attendance: Any?,
    val awayTeam: Team,
    val bookings: List<Any>?,
    val competition: Competition?,
    val goals: List<Any>?,
    val group: String?,
    val homeTeam: Team,
    val id: Int,
    val injuryTime: Any?,
    val lastUpdated: String?,
    val matchday: Int,
    val minute: Any?,
    val odds: Odds?,
    val penalties: List<Any>?,
    val referees: List<Any>?,
    val score: Score,
    val season: Season?,
    val stage: String?,
    val status: String?,
    val substitutions: List<Any>?,
    val utcDate: String?,
    val venue: String?
)