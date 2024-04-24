package com.example.footballapidemo.data

data class Person(
    val team: Team?,
    val dateOfBirth: String,
    val firstName: String,
    val id: Int,
    val lastName: String,
    val marketValue: Int,
    val lastUpdated: String,
    val name: String,
    val nationality: String,
    val position: String,
    val shirtNumber: Int,
    val runningCompetitions: List<RunningCompetition>?,
    val contract: Contract?
)