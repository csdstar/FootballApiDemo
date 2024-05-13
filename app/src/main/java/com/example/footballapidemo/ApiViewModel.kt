package com.example.footballapidemo

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class ApiViewModel : ViewModel() {

    companion object {

        val message = mutableStateOf("")

        //非挂起函数，直接在后台调用回调函数来修改viewModel中的数据
        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        fun <T, R : ViewModel> testApi(
            apiFunc: suspend () -> Response<T>,  //要调用的Api请求，T是response的类型
            objectiveViewModel: R,  //传入回调函数进行输出或更改的viewModel
            bodyCallback: (T, R) -> Unit  //函数从response中获取body后在此回调函数中处理，输出到viewModel
        ) {
            objectiveViewModel.viewModelScope.launch {
                val body = testApi(apiFunc)
                body?.let { bodyCallback(body, objectiveViewModel) }
            }
        }

        //挂起函数，返回网络请求response.body()
        @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
        suspend fun <T> testApi(apiFunc: suspend () -> Response<T>): T? {
            var attempts = 0
            val maxAttempts = 3
            var response: Response<T>? = null

            //由于网络连接不稳，设置多次请求数据
            while (response == null && attempts < maxAttempts) {
                attempts++
                Log.d(TAG, "loading, attempts: $attempts")
                message.value = "loading, attempts: $attempts"
                try {
                    response = withContext(Dispatchers.IO) {
                        apiFunc.invoke()
                    }
                } catch (e: IOException) {
                    Log.e("MyTag", "IOException,you may not have Internet connection")
                    message.value = "IOException,you may not have Internet connection"
                } catch (e: HttpException) {
                    Log.e("MyTag", "HTTPException, you may not have access")
                    message.value = "HTTPException, you may not have access"
                }
                delay(300)
            }

            return if (attempts >= maxAttempts) {
                message.value = "reached max attempts, no response"
                Log.e(TAG, "reached max attempts, no response")
                null
            } else if (response?.body() != null) {
                val body: T = response.body()!!
                Log.d(TAG, body.toString())
                message.value = "success"
                body
            } else {
                Log.e(TAG, "no body")
                message.value = "response no body"
                null
            }
        }
    }
}