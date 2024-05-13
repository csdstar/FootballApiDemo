package com.example.footballapidemo.ui.matches

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.footballapidemo.MatchesViewModel
import com.example.footballapidemo.TAG
import com.lt.compose_views.menu_fab.MenuFabItem
import com.lt.compose_views.menu_fab.MenuFloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

val menuItems = ArrayList<MenuFabItem>().apply {
    add(
        MenuFabItem(
            icon = {
                Icon(
                    modifier = Modifier.size(16.dp),
                    imageVector = Icons.Rounded.Home,
                    contentDescription = "",
                    tint = Color.White
                )
            },
            label = "重置"
        )
    )
    add(
        MenuFabItem(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Search,
                    modifier = Modifier.size(16.dp),
                    contentDescription = "",
                    tint = Color.White
                )
            },
            label = "查找"
        )
    )
}

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun FloatingButtonCompose(modifier: Modifier, viewModel: MatchesViewModel) {
    var isSearching by viewModel.isSearching
    val index by viewModel.index

    //悬浮查找按钮
    MenuFloatingActionButton(
        srcIcon = Icons.Filled.Add,
        items = menuItems,
        modifier = modifier
    ) {
        if (it.label == "查找") {
            Log.d(TAG, "查找")
            isSearching = true
        }
        if (it.label == "重置") {
            CoroutineScope(Dispatchers.IO).launch {
                viewModel.clearMap(index)
                viewModel.initDate(index)
                pageInitialize(viewModel, index)
            }
        }
    }
}