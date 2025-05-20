package com.example.myapplication1211

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitClient {
    //private const val BASE_URL = "http://192.168.31.171:5000/" // 确保使用 HTTP#192.168.1.100
    //private const val BASE_URL = "http://192.168.31.189:5000/"
    //private const val BASE_URL = "http://192.168.31.206:5000/"
    private const val BASE_URL = "https://recipeapi-jd4i.onrender.com/"
    val apiService: ApiService by lazy {
        val logging = HttpLoggingInterceptor()
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .build()

        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }
}
