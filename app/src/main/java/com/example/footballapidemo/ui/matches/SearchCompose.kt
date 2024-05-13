package com.example.footballapidemo.ui.matches

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.footballapidemo.MatchesViewModel
import com.example.footballapidemo.addMatchesByCompetitionCode
import com.example.footballapidemo.formatDate
import com.example.footballapidemo.isDateInvalid
import com.lt.compose_views.value_selector.date_selector.DateSelector
import com.lt.compose_views.value_selector.date_selector.DateSelectorState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@OptIn(ExperimentalFoundationApi::class)
@Composable
fun SearchCompose(viewModel: MatchesViewModel) {
    var isSearching by viewModel.isSearching
    val dateState1 = remember { DateSelectorState(2024, 1, 1) }
    val dateState2 = remember { DateSelectorState(2024, 1, 1) }
    Dialog(onDismissRequest = { isSearching = false }) {
        Column {
            Column(
                modifier = Modifier
                    .background(Color.White)
                    .alpha(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "起始日期")
                DateSelector(
                    state = dateState1,
                    isLoop = true
                )
            }

            Spacer(Modifier.height(20.dp))

            Column(
                modifier = Modifier
                    .background(Color.White)
                    .alpha(0.5f),
                horizontalAlignment = Alignment.CenterHorizontally
            ){
                Text(text = "终止日期")
                DateSelector(
                    state = dateState2,
                    isLoop = true
                )
            }
            
            Row(
                Modifier.fillMaxWidth(),
                Arrangement.Center,
                Alignment.CenterVertically
            ) {
                Button(
                    onClick = {
                        searchMatches(viewModel, dateState1, dateState2)
                    }
                ) {
                    Text(text = "确认")
                }
                Spacer(modifier = Modifier.width(20.dp))
                Button(
                    onClick = { isSearching = false }
                ) {
                    Text(text = "取消")
                }
            }
        }
    }
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
fun searchMatches(
    viewModel: MatchesViewModel,
    dateState1: DateSelectorState,
    dateState2: DateSelectorState
) {
    //按具体时间查找比赛
    var isLoading by viewModel.isLoading
    var isSearching by viewModel.isSearching
    val index = viewModel.index.intValue

    CoroutineScope(Dispatchers.IO).launch {
        isSearching = false
        val dateFrom = formatDate(dateState1.getYear(), dateState1.getMonth(), dateState1.getDay())
        val dateTo = formatDate(dateState2.getYear(), dateState2.getMonth(), dateState2.getDay())
        if(isDateInvalid(dateFrom, dateTo)){
            cancel()
        }
        else{
            isLoading = true
            viewModel.clearMap(index)
            viewModel.setDate(index, dateFrom, dateTo)
            addMatchesByCompetitionCode(
                MatchesViewModel.competitionsCode[index],
                dateFrom, dateTo,
                viewModel
            )
            isLoading = false
        }
    }
}