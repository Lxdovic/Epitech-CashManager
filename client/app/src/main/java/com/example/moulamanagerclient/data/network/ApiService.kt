package com.example.moulamanagerclient.data.network

import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.*

interface ApiService {
    @GET(ApiEndpoints.BARCODE)
    suspend fun getProducts(@Path("barcode") barcode: String): Response<ProductResponse>

    @POST(ApiEndpoints.LOGIN)
    suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
