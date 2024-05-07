package com.example.footballapidemo.news

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import coil.compose.rememberImagePainter
import com.example.footballapidemo.ApiViewModel

@RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
@Composable
fun NewsScreen(viewModel: NewsViewModel) {
    val newsList = viewModel.newsList
    val api = NewsRetrofitInstance.api
    Surface {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(20.dp)
                .background(Color.Yellow)
        )
        {
            Button(
                onClick = {
                    ApiViewModel.testApi(
                        { api.getNews() },
                        viewModel,
                        ::newsJsonCallback
                    )
                    Log.d("MyTag","Clicked")
                }
            ) {
                Text(text = "addNews")
            }
        }
        LazyColumn(
            modifier = Modifier,
            userScrollEnabled = true,
            contentPadding = PaddingValues(0.dp)
        ) {
            items(viewModel.newsList){
                newsRow(it)
            }
        }
    }
}

@Composable
fun newsRow(news: News) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .border(
                border = BorderStroke(width = 1.dp, color = Color.Black)
            )
            .aspectRatio(9f / 2f)  //设置长宽比
            .background(color = Color.White)
    ) {
        Row(
            modifier = Modifier
                .fillMaxSize()
                .padding(2.dp)
        ) {
            // 左侧部分：标题和描述
            Column(
                modifier = Modifier
                    .weight(3f)
                    .padding(horizontal = 8.dp)
            ) {
                Text(
                    text = news.title,
                    style = MaterialTheme.typography.bodyLarge,
                    modifier = Modifier.weight(3f)
                )
                Text(
                    text = news.ctime.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    modifier = Modifier.weight(1f)
                )
            }
            Surface(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxSize()
            ) {
                Image(
                    painter = rememberImagePainter(news.picUrl),
                    contentDescription = ""
                )
            }
        }
    }
}