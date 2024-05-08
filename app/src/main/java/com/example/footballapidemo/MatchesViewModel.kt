package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.runtime.MutableIntState
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.snapshots.SnapshotStateMap
import androidx.lifecycle.ViewModel
import com.example.footballapidemo.data.Match
import java.time.LocalDate

@RequiresApi(Build.VERSION_CODES.O)
class MatchesViewModel : ViewModel() {
    companion object {
        val competitions =
            listOf("全部", "英超PL", "欧冠CL", "法甲FL1", "德甲BL1", "意甲SA")
        val competitionsCode = listOf("", "PL", "CL", "FL1", "BL1", "SA")
    }

    data class PageData(
        val matchGroups: SnapshotStateMap<LocalDate, SnapshotStateList<Match>>,
        val dateFrom: MutableState<String>,
        val dateTo: MutableState<String>
    )
    //以日期作为键，列表作为值

    private val _pagesData = mutableStateListOf<PageData>()
    private val _index = mutableIntStateOf(0)

    init {
        val curDate = mutableStateOf(getCurrentDateString())
        // 初始化页面数据
        repeat(competitions.size) { _ ->
            val matchGroups = mutableStateMapOf<LocalDate, SnapshotStateList<Match>>()
            val dateFrom = mutableStateOf(getDateStringWithOffset(curDate.value,3,false))
            val dateTo = mutableStateOf(getDateStringWithOffset(curDate.value,3,true))
            //创建若干次state对象，确保每个pageData的变量独立
            _pagesData.add(PageData(matchGroups,dateFrom,dateTo))
        }
    }
    val pagesData: List<PageData>
        get() = _pagesData

    val index: MutableIntState
        get() = _index

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMatch(index: Int, match: Match) {
        val date = convertUtcToChinaLocalDate(match.utcDate)
        val matchListByDate = _pagesData[index].matchGroups
        val matches = matchListByDate.getOrPut(date){ mutableStateListOf() }
        if(!matches.contains(match)) {
            matches.add(match)
        }
    }
}