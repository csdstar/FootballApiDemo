package com.example.footballapidemo

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException

class ApiViewModel : ViewModel() {
    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

    companion object {
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

        suspend fun <T> testApi( apiFunc: suspend () -> Response<T> ): T? {
            var attempts = 0
            val maxAttempts = 4
            var response: Response<T>? = null

            //由于网络连接不稳，设置多次请求数据
            while (response == null && attempts < maxAttempts) {
                attempts++
                Log.d(TAG, "attempts: $attempts")
                try {
                    response = withContext(Dispatchers.IO) {
                        apiFunc.invoke()
                    }
                } catch (e: IOException) {
                    Log.e("MyTag", "IOException,you may not have Internet connection")
                } catch (e: HttpException) {
                    Log.e("MyTag", "HTTPException, you may not have access")
                }
                delay(300)
            }
            Log.d(TAG, response.toString())

            return if (response?.body() != null) {
                val body: T = response.body()!!
                Log.d(TAG, body.toString())
                body
            } else {
                Log.e(TAG, "no response or no body")
                null
            }
        }
    }
}