package com.example.footballapidemo.data

data class Scorer(
    val player: Person,
    val team: Team,
    val goals: Int,
    val assist: Int,
    val penalties: Int
)
