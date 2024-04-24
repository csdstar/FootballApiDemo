package com.example.footballapidemo

import android.net.http.HttpException
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresExtension
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import retrofit2.Response
import java.io.IOException


class ApiViewModel:ViewModel() {
    //注意这里导入的State是runtime包里的
    private var _text = mutableStateOf("abcdefg")
    var text: State<String> = _text

    fun changeText(textToChnage:String){
        _text.value = textToChnage
    }

    fun addText(textToAdd:String?){
        _text.value += textToAdd
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)

    fun <T,R: ViewModel> testApi(
        apiFunc: suspend () -> Response<T>,  //要调用的Api请求，T是response的类型
        bodyCallback: (T, R) -> Unit,  //函数从response中获取body后在此回调函数中处理，输出到viewModel
        objectiveViewModel: R  //传入回调函数进行输出或更改的viewModel
    ) {
        viewModelScope.launch {
            var attempts = 0
            val maxAttempts = 4
            var response: Response<T>? = null

            //由于网络连接不稳，设置多次请求数据
            while(response == null && attempts<maxAttempts){
                attempts++
                Log.d(TAG,"attempts: $attempts")
                try{
                    response = withContext(Dispatchers.IO){
                        apiFunc.invoke()
                    }
                }catch (e: IOException){
                    Log.e("MyTag","IOException,you may not have Internet connection")
                }catch (e: HttpException){
                    Log.e("MyTag","HTTPException, you may not have access")
                }
                delay(200)
            }

            if (response?.body() != null) {
                val body:T = response.body()!!
                bodyCallback(body, objectiveViewModel)
                return@launch
            }
            else{
                Log.e(TAG,"no response or no body")
                changeText("no response or no body")
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getTeamById(teamId:Int){
        viewModelScope.launch{
            val result = async(Dispatchers.IO) {
                try {
                    RetrofitInstance.api.getTeamById(teamId)
                }catch (e: IOException){
                    Log.e("MyTag","IOException,you may not have Internet connection")
                    return@async null
                }catch (e: HttpException){
                    Log.e("MyTag","HTTPException, you may not have access")
                    return@async null
                }
            }

            val response = result.await()
            if (response != null){
                val team = response.body()!!
                Log.d(TAG,team.name.toString())
                Log.d(TAG,team.id.toString())
                changeText("name = ${team.name}  id = ${team.id.toString()}")
            }
            else{
                Log.e(TAG,"no return")
                changeText("error")
            }
        }
    }

    @RequiresExtension(extension = Build.VERSION_CODES.S, version = 7)
    fun getMatches(){
        viewModelScope.launch{
            val result = async(Dispatchers.IO){
                try {
                    RetrofitInstance.api.getMatches()
                }catch (e: IOException){
                    Log.e("MyTag","IOException,you may not have Internet connection")
                    return@async null
                }catch (e: HttpException){
                    Log.e("MyTag","HTTPException, you may not have access")
                    return@async null
                }
            }
            val response = result.await()
            if(response?.body() != null){
                // Log.d(TAG,response.body().toString())
                val matches = response.body()!!.matches
                changeText(matches.size.toString())
                Log.d(TAG,matches.size.toString())
                for(match in matches){
                    val awayTeam = match.awayTeam.name
                    val homeTeam = match.homeTeam.name
                    changeText("away = $awayTeam  home = $homeTeam")
                    Log.d(TAG,"away = $awayTeam  home = $homeTeam")
                }
            }
            else{
                Log.e(TAG,"get matches error")
                changeText("error")
            }
        }
    }



}