package com.example.moulamanagerclient.data.repositories

import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.product.Product
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ApiService {
    @POST(ApiEndpoints.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>

    @GET(ApiEndpoints.PRODUCTS)
    suspend fun getProducts(): Response<Pagination<Product>>
}
