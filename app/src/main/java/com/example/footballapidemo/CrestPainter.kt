package com.example.footballapidemo

import android.util.Log
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Refresh
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import coil.compose.SubcomposeAsyncImage
import coil.request.CachePolicy
import coil.request.ImageRequest

@Composable
fun CrestImage(picUrl: String?) {
    val context = LocalContext.current
    var mutUrl by remember { mutableStateOf(picUrl) }
    SubcomposeAsyncImage(
        modifier = Modifier
            .aspectRatio(1f / 1f)
            .fillMaxHeight(),
        model = ImageRequest
            .Builder(context)
            .data(mutUrl)
            .crossfade(false)
            .allowHardware(true)
            .networkCachePolicy(CachePolicy.ENABLED)
            .memoryCachePolicy(CachePolicy.ENABLED)
            .build(),
        contentDescription = picUrl,
        contentScale = ContentScale.FillBounds,
        onError = { Log.e(TAG, "PainterError,url: $picUrl") },
        loading = {
            Box(modifier = Modifier.fillMaxSize(0.8f)) {
                CircularProgressIndicator(Modifier.fillMaxSize())
            }
        },
        error = {
            Icon(
                imageVector = Icons.Default.Refresh,
                contentDescription = "",
                Modifier.clickable {
                    mutUrl = null
                    mutUrl = picUrl
                }
            )
        }
    )

    //    AsyncImage(
//        modifier = Modifier
//            .aspectRatio(1f/1f)
//            .fillMaxHeight(),
//        model = ImageRequest
//            .Builder(context)
//            .data(picUrl)
//            .crossfade(false)
//            .allowHardware(true)
//            .networkCachePolicy(CachePolicy.ENABLED)
//            .memoryCachePolicy(CachePolicy.ENABLED)
//            .build(),
//        contentDescription = "",
//        error = painterResource(id = R.drawable.ic_launcher_foreground),
//        placeholder = painterResource(id = R.drawable.ic_launcher_background),
//        onError = { Log.e(TAG, "PainterError,url: $picUrl") },
//        contentScale = ContentScale.FillBounds
//    )
}