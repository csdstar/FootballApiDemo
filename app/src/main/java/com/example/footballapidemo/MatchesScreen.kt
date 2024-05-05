package com.example.footballapidemo

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.lt.compose_views.compose_pager.ComposePagerState
import com.lt.compose_views.compose_pager.rememberComposePagerState
import kotlinx.coroutines.launch

@Composable
fun MatchesScreen(viewModel: MatchesViewModel) {
    val composePagerState = remember { ComposePagerState() }
    Column {
//        VerticalRefreshableLayout(
//            topRefreshLayoutState = rememberRefreshLayoutState(onRefreshListener = TODO()),
//            bottomRefreshLayoutState = rememberRefreshLayoutState(onRefreshListener = TODO()),
//            topUserEnable = composePagerState.getCurrSelectIndex() == 0,
//        ) {
//
//        }
        PagerWithTabRow(viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)
@Composable
fun PagerWithTabRow(viewModel: MatchesViewModel) {
    val competitions = MatchesViewModel.competitions
    val competitionsCode = MatchesViewModel.competitionsCode

    val pagerState = rememberPagerState(
        initialPage = 0,
        initialPageOffsetFraction = 0f
    ) { competitions.size }
    val composePagerState = rememberComposePagerState()
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        ScrollableTabRow(selectedTabIndex = pagerState.currentPage) {
            competitions.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(index)
                        }
                    },
                    text = { Text(text = tab) }
                )
            }
        }

        HorizontalPager(state = pagerState) {
            Text(text = competitionsCode[pagerState.currentPage])
            val code = competitionsCode[pagerState.currentPage]
        }
    }
}

fun getMatchesByCompetitionCode(code:String){

}