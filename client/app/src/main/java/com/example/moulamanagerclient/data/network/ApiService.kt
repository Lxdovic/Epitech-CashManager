package com.example.moulamanagerclient.data.network

import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
//import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
    @POST("login")
    fun login(@Body request: LoginRequest): Response<LoginResponse>

//    @GET("dashboard")
//    suspend fun getDashboardData():Response<DashboardResponse>
}
