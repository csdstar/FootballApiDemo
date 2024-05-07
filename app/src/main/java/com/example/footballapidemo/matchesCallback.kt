package com.example.footballapidemo

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