package com.example.moulamanagerclient.utils

import okhttp3.logging.HttpLoggingInterceptor
import com.example.moulamanagerclient.data.repositories.ApiEndpoints
import com.example.moulamanagerclient.data.repositories.ApiHeader
import com.example.moulamanagerclient.data.repositories.ApiService
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

object Retrofit {
    private val builder: OkHttpClient.Builder = OkHttpClient().newBuilder()
    private val loggingInterceptor = HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY }

    init {
        builder.connectTimeout(3, TimeUnit.SECONDS)
            .readTimeout(20, TimeUnit.SECONDS)
            .writeTimeout(25, TimeUnit.SECONDS)
            .addInterceptor { chain ->
                val newRequest = ApiHeader.getAccessToken.let {
                    chain.request().newBuilder()
                        .addHeader("Authorization", "Bearer $it")
                        .build()
                }
                chain.proceed(newRequest)
            }
            .addInterceptor(loggingInterceptor)
    }

    private val client = builder.build()
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiEndpoints.BASE_URL)
        .client(client)
        .addConverterFactory(MoshiConverterFactory.create())
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}