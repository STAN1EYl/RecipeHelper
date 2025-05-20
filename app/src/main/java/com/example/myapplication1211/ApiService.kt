package com.example.myapplication1211

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("/api/get_all_data") // get all data
    fun getImages(): Call<ApiResponse>

    @GET("/api/search_data") // get search data
    fun searchData(@Query("query") query: String): Call<ApiResponse>
}

