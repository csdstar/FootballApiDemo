package com.example.footballapidemo.data

import java.util.Date

data class Filters(
    val id : Int?,
    val matchday : Int?,
    val season: Int?,
    val competitions: String?,
    val limit: Int?,
    val permission: String?,
    val offset : Int?,
    val status : Status?,
    val venue : Venue?,
    val date : String?,
    val dateFrom : String?,
    val dateTo : String?
)