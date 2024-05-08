package com.example.footballapidemo

import com.example.footballapidemo.data.MatchesJson
import com.example.footballapidemo.data.Person
import com.example.footballapidemo.data.Team
import com.example.footballapidemo.data.Teams
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface DataInterface {
    @GET("teams/")
    suspend fun getTeams():Response<Teams>
    //OK

    @GET("teams/{teamId}/")
    suspend fun getTeamById(
        @Path("teamId") teamId: Int
    ):Response<Team>
    //OK

    @GET("matches")
    suspend fun getMatches(
        @Query("dateFrom") dateFrom: String = "",
        @Query("dateTo") dateTo: String = "",
    ):Response<MatchesJson>
    //OK

    // 获取特定联赛的比赛数据,api定义联赛是competition，这里用个人习惯的league
    @GET("competitions/{competitionCode}/matches")
    suspend fun getMatchesByCompetition(
        @Path("competitionCode") competitionCode:String,
        @Query("dateFrom") dateFrom: String = "",
        @Query("dateTo") dateTo: String = "",
    ):Response<MatchesJson>
    //OK


    @GET("competitions/{league}/teams")
    suspend fun getLeagueTeams(
        @Path("league") league:String
    ):Response<Teams>
    //OK

    @GET("teams/{teamId}/matches")
    suspend fun getTeamMatchesByTeamId(
        @Path("teamId") teamId: Int,
        @Query("status") status: String = "",
        @Query("date") date: String = "",
        @Query("dateFrom") dateFrom: String = "",
        @Query("dateTo") dateTo: String = "",
        @Query("season") season:Int = 2023
    ): Response<MatchesJson>

    @GET("persons/{personId}")
    suspend fun getPersonById(
        @Path("personId") personId: Int
    ): Response<Person>
}