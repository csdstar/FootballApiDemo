package com.example.footballapidemo

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.lazy.LazyListState
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
            listOf("全部", "欧冠", "英超", "法甲", "德甲", "意甲", "巴甲", "英冠")
        val competitionsCode =
            listOf("", "CL", "PL", "FL1", "BL1", "SA", "BSA", "ELC")
        //所有联赛的名字及索引
    }

    data class PageData(
        val matchGroups: SnapshotStateMap<LocalDate, SnapshotStateList<Match>>,
        //该页面的赛程分组（按日期）
        var listState: LazyListState?,
        val dateFrom: MutableState<String>,
        //该页面显示的最早赛程时间
        val dateTo: MutableState<String>
        //该页面显示的最晚赛程时间
    )

    private val _pagesData = mutableStateListOf<PageData>()
    //页面数据列表

    private val _index = mutableIntStateOf(0)
    //当前页面索引

    private val _isLoading = mutableStateOf(true)
    //页面加载状态

    private val _isSearching = mutableStateOf(false)

    val pagesData: List<PageData>
        get() = _pagesData

    val index: MutableIntState
        get() = _index

    val isLoading: MutableState<Boolean>
        get() = _isLoading

    val isSearching: MutableState<Boolean>
        get() = _isSearching

    init {  //初始化每个页面的数据
        val curDate = getCurrentDateString()
        repeat(competitions.size) { _ ->
            val matchGroups = mutableStateMapOf<LocalDate, SnapshotStateList<Match>>()
            val lazyListState: LazyListState? = null
            val dateFrom = mutableStateOf(getDateStringWithOffset(curDate, 5, false))
            val dateTo = mutableStateOf(getDateStringWithOffset(curDate, 5, true))
            //创建若干次state对象，确保每个pageData的变量独立
            _pagesData.add(PageData(matchGroups, lazyListState, dateFrom, dateTo))
        }
        _pagesData[0].dateFrom.value = getDateStringWithOffset(curDate, 2, false)
        _pagesData[0].dateTo.value = getDateStringWithOffset(curDate, 2, true)
        //由于“全部”页面的数据量较大，单独初始化其显示日期
    }

    fun setDateFrom(index: Int, dateFrom: String) {
        _pagesData[index].dateFrom.value = dateFrom
    }

    fun setDateTo(index: Int, dateTo: String) {
        _pagesData[index].dateTo.value = dateTo
    }

    fun setDate(index: Int, dateFrom: String, dateTo: String) {
        _pagesData[index].dateFrom.value = dateFrom
        _pagesData[index].dateTo.value = dateTo
    }

    fun initDate(index: Int) {
        val days: Long = if (index == 0) 2 else 5
        val date = getCurrentDateString()
        val dateFrom = getDateStringWithOffset(date, days, false)
        val dateTo = getDateStringWithOffset(date, days, true)
        setDate(index, dateFrom, dateTo)
    }

    fun setListState(index: Int, state: LazyListState) {
        _pagesData[index].listState = state
    }

    fun clearMap(index: Int) {
        _pagesData[index].matchGroups.clear()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun addMatch(index: Int, match: Match) {
        val date = convertUtcToChinaLocalDate(match.utcDate)
        val matchListByDate = _pagesData[index].matchGroups
        val matches = matchListByDate.getOrPut(date) { mutableStateListOf() }
        if (!matches.contains(match)) {
            matches.add(match)
        }
    }
}