@file:RequiresApi(Build.VERSION_CODES.O)
@file:RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import com.example.footballapidemo.data.MatchesJson


fun competitionMatchesCallback(matchesJson: MatchesJson, viewModel: MatchesViewModel) {
    val code = matchesJson.competition.code
    val index = MatchesViewModel.competitionsCode.indexOf(code)
    val matches = matchesJson.matches
    matches.forEach {
        viewModel.addMatch(index, it)
    }
}

fun matchesCallback(matchesJson: MatchesJson, viewModel: MatchesViewModel) {
    val matches = matchesJson.matches
    matches.forEach {
        viewModel.addMatch(0, it)
    }
}


suspend fun addMatchesByCompetitionCode(
    code: String, dateFrom: String, dateTo: String, viewModel: MatchesViewModel
) {
    val api = RetrofitInstance.api
    if (code.isNotBlank()) {
        val matchesJson = ApiViewModel.testApi {
            api.getMatchesByCompetition(code, dateFrom, dateTo)
        }
        val matches = matchesJson?.matches
        val competitionCode = MatchesViewModel.competitionsCode
        val index = competitionCode.indexOf(code)
        matches?.forEach {
            viewModel.addMatch(index, it)
        }
    }
    else {
        val matchesJson = ApiViewModel.testApi {
            api.getMatches(dateFrom,dateTo)
        }
        val matches = matchesJson?.matches
        matches?.forEach {
            viewModel.addMatch(0, it)
        }
    }
}


fun getMatchesByCompetitionCode(code: String, viewModel: MatchesViewModel) {
    val api = RetrofitInstance.api
    if (code != "") {
        ApiViewModel.testApi(
            apiFunc = { api.getMatchesByCompetition(code) },
            objectiveViewModel = viewModel,
            bodyCallback = ::competitionMatchesCallback,
        )
    } else {
        ApiViewModel.testApi(
            { api.getMatches() },
            viewModel,
            ::matchesCallback
        )
    }
}