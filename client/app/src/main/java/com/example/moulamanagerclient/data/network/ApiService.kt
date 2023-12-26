package com.example.moulamanagerclient.data.network

import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.product.ProductResponse
import retrofit2.Response
import retrofit2.http.*

interface ApiService {
	@GET(ApiEndpoints.BARCODE)
	suspend fun getProductsByBarcode(@Path("barcode") barcode: String): Response<ProductResponse>

	@GET(ApiEndpoints.PRODUCTS)
	suspend fun getProducts(@Query("page") page: Int, @Query("size") size: Int): Response<Pagination<ProductResponse>>

	@GET(ApiEndpoints.PRODUCTS_BY_NAME)
	suspend fun searchProductsByName(
		@Path("name") name: String,
		@Query("page") page: Int,
		@Query("size") size: Int
	): Response<Pagination<ProductResponse>>

	@POST(ApiEndpoints.LOGIN)
	suspend fun login(@Body request: LoginRequest): Response<LoginResponse>
}
