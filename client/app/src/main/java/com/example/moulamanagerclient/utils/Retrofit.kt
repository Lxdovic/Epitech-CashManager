package com.example.moulamanagerclient.utils

import com.example.moulamanagerclient.data.repositories.ApiService
import com.example.moulamanagerclient.data.repositories.ApiURL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object Retrofit {
    private val client = OkHttpClient.Builder().build()
    private val retrofit= Retrofit.Builder()
        .baseUrl(ApiURL.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
    val apiService = retrofit.create(ApiService::class.java);
}