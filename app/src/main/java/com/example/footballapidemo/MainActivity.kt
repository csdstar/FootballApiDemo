package com.example.footballapidemo

import android.os.Build
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.footballapidemo.news.NewsScreen
import com.example.footballapidemo.news.NewsViewModel

const val TAG = "MyTag"

class MainActivity : ComponentActivity() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val viewModel = MatchesViewModel()
        setContent {
            MatchesScreen(viewModel)
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun ApiTest(viewModel: ApiViewModel) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Yellow)
    ){
        Text(viewModel.text.value)
    }

    val api = RetrofitInstance.api


    LaunchedEffect(Unit){
        ApiViewModel.testApi(
            apiFunc = {api.getTeamById(64)},
            ::teamCallback,
            viewModel
        )


//        viewModel.testApi(
//            {api.getLeagueTeams("PL")},
//            ::teamsCallback
//        )
    }
}