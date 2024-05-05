package com.example.footballapidemo

import android.util.Log
import com.example.footballapidemo.data.Matches
import com.example.footballapidemo.data.Person
import com.example.footballapidemo.data.Team
import com.example.footballapidemo.data.Teams

fun matchesCallback(matchesJson:Matches, viewModel: ApiViewModel){
    viewModel.changeText("")
    for(match in matchesJson.matches){
        Log.d(TAG,"home = ${match.homeTeam.name} away = ${match.awayTeam.name} date = ${match.utcDate}")
        viewModel.addText(match.homeTeam.name+" id = "+match.homeTeam.id+"\n")
        viewModel.addText(match.awayTeam.name+" id = "+match.awayTeam.id+"\n")
        viewModel.addText("winner: "+match.score.winner.toString()+"\n")
        viewModel.addText("date:"+match.utcDate.toString()+"\n")
    }
}

fun teamsCallback(teamsJson:Teams, viewModel: ApiViewModel){
    viewModel.changeText("")
    for(team in teamsJson.teams){
        Log.d(TAG,"team = ${team.name} team id = ${team.id}")
        viewModel.addText("team = ${team.name} team id = ${team.id} \n\n")
    }
}

fun teamCallback(team: Team, viewModel: ApiViewModel){
    viewModel.changeText("")
    viewModel.addText("name = ${team.name}\n id = ${team.id}\n\n")
    viewModel.addText("coach: ${team.coach?.name} id: ${team.coach?.id}\n")
    viewModel.addText("squad:\n")
    for(person in team.squad)
        viewModel.addText("name: ${person.name} id: ${person.id}\n")
}

fun personCallback(person: Person, viewModel: ApiViewModel){
    viewModel.changeText("")
    viewModel.addText("name: ${person.name} \n")
}