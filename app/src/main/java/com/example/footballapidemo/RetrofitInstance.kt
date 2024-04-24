package com.example.footballapidemo

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance{
    private const val BASE_URL = "https://api.football-data.org/v4/"
    //BASE_URL必须以/结尾
    private const val API_KEY = "c07c3b803ea6471fa7208842a6946e0c"

    private val client = OkHttpClient.Builder().apply {
        addInterceptor(Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader("X-Auth-Token", API_KEY)
                .build()
            chain.proceed(request)
        })
    }.build()
    //构建OkHTTPClient对象，用于在每次使用api实例时添加api key

    val api: FootballDataInterface by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataInterface::class.java)
    }

}