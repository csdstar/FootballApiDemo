package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.footballapidemo.data.Match
import com.example.footballapidemo.data.Team
import com.lt.compose_views.compose_pager.ComposePager
import com.lt.compose_views.compose_pager.rememberComposePagerState
import com.lt.compose_views.refresh_layout.RefreshContentStateEnum
import com.lt.compose_views.refresh_layout.RefreshLayoutState
import com.lt.compose_views.refresh_layout.VerticalRefreshableLayout
import com.lt.compose_views.refresh_layout.rememberRefreshLayoutState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MatchesScreen(viewModel: MatchesViewModel) {
    MatchesScreenDetail(viewModel)
}

@RequiresApi(Build.VERSION_CODES.O)
@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun MatchesScreenDetail(viewModel: MatchesViewModel) {
    val competitions = MatchesViewModel.competitions
    val competitionsCode = MatchesViewModel.competitionsCode

    val composePagerState = rememberComposePagerState()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScrollableTabRow(
            selectedTabIndex = composePagerState.getCurrSelectIndexState().value
        ) {
            competitions.forEachIndexed { index, tab ->
                Tab(
                    selected = composePagerState.getCurrSelectIndexState().value == index,
                    onClick = {
                        composePagerState.setPageIndex(index)
                    },
                    text = { Text(text = tab) }
                )
            }
        }
        VerticalRefreshableLayout(
            topRefreshLayoutState = rememberRefreshLayoutState(
                onRefreshListener = topRefresh(viewModel)
            ),  //顶部刷新
            bottomRefreshLayoutState = rememberRefreshLayoutState(
                onRefreshListener = bottomRefresh(viewModel)
            ),  //底部加载
        ) {
            ComposePager(
                pageCount = competitions.size,
                modifier = Modifier.fillMaxSize(),
                composePagerState = composePagerState,
                orientation = Orientation.Horizontal,
                pageCache = 2
            ) {
                val curIndex by composePagerState.getCurrSelectIndexState()
                val matches = remember(curIndex) { viewModel.pagesData[curIndex].matchList }
                Column {
                    Button(
                        onClick = {
                            getMatchesByCompetitionCode(
                                competitionsCode[curIndex],
                                viewModel
                            )
                        },
                        modifier = Modifier
                            .fillMaxWidth(0.5f)
                            .height(50.dp)
                    ) {
                        Text(text = curIndex.toString())
                    }
                    Spacer(modifier = Modifier.height(20.dp))
                    MatchGroup(matches = matches)
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
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

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchRow(match: Match) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        // 左边球队信息
        LeftTeamBox(modifier = Modifier.weight(1f), team = match.homeTeam)

        // 中间数据
        Box(
            modifier = Modifier
                .weight(0.4f)
                .fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                if (match.status == "FINISHED") {
                    Text(
                        text = "${match.score.fullTime.home} - ${match.score.fullTime.away}",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                } else {
                    Text(
                        text = "VS",
                        style = TextStyle(fontWeight = FontWeight.Bold),
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )
                }
                Text(
                    text = convertUtcToChinaTime(match.utcDate),
                    fontSize = 10.sp,
                    style = TextStyle(fontWeight = FontWeight.Bold),
                    modifier = Modifier.padding(horizontal = 6.dp)
                )
            }
        }

        // 右边球队信息
        RightTeamBox(modifier = Modifier.weight(1f), team = match.awayTeam)
    }
}

@Composable
fun LeftTeamBox(modifier: Modifier, team: Team) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Green),
    ) {
        Row(
            modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.padding(10.dp),
                text = team.shortName.toString()
            )
            Box(
                modifier = Modifier.width(50.dp)
            ) {
                CrestImage(picUrl = team.crest)
            }
        }
    }
}

@Composable
fun RightTeamBox(modifier: Modifier, team: Team) {
    Box(
        modifier = modifier
            .fillMaxHeight()
            .background(Color.Green),
    ) {
        Row(
            Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier.width(50.dp)
            ) {
                CrestImage(picUrl = team.crest)
            }
            Text(
                modifier = Modifier.padding(10.dp),
                text = team.shortName.toString()
            )
        }
    }
}

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MatchGroup(matches: List<Match>) {
    val matchGroups = matches.groupBy {
        val chinaTime = convertUtcToChinaDate(it.utcDate)
        LocalDate.parse(chinaTime.split(" ")[0])
    }

    LazyColumn(modifier = Modifier.fillMaxSize()) {
        matchGroups.forEach { (date, matches) ->
            item {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .height(70.dp)
                        .background(Color.LightGray),
                    horizontalArrangement = Arrangement.Center,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = date.toString())
                }
            }
            items(matches) { match ->
                MatchRow(match = match)
                Spacer(modifier = Modifier.height(5.dp))
            }
        }
    }
}

fun topRefresh(
    viewModel: MatchesViewModel
): (RefreshLayoutState.() -> Unit) = {
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        setRefreshState(RefreshContentStateEnum.Stop)
    }
}

private fun bottomRefresh(
    viewModel: MatchesViewModel
): (RefreshLayoutState.() -> Unit) = {
    CoroutineScope(Dispatchers.IO).launch {
        delay(1000)
        setRefreshState(RefreshContentStateEnum.Stop)
    }
}
