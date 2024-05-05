package com.example.footballapidemo

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import com.example.footballapidemo.data.Match

class MatchesViewModel : ViewModel() {
    companion object{
        val competitions = listOf("全部", "英超PL", "欧冠CL", "法甲FL1", "欧联杯EC", "德甲BL1", "意甲SA")
        val competitionsCode = listOf("","PL","CL","FL1","EC","BL1","SA")
    }
    data class PageData(val matchList: SnapshotStateList<Match>)
    //用数据类来装载可观测列表matchList，再将若干数据类装入另一个可观测列表中

    private val pagesData = mutableStateListOf<PageData>()
    // 创建可观察的页面数据列表，索引对应于页面的索引

    init {
        // 初始化页面数据
        repeat(competitions.size) { pageIndex ->
            val matchList = mutableStateListOf<Match>()
            pagesData.add(PageData(matchList))
        }
    }
}