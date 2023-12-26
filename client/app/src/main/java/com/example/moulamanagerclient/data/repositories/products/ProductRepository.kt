package com.example.moulamanagerclient.data.repositories.products

import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiHelper.handleApiResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(
	private val apiService: ApiService
) {

	suspend fun getProducts(page: Int): ApiResult<Pagination<ProductResponse>> {
		return withContext(Dispatchers.IO) {
			handleApiResponse(
				request = { apiService.getProducts(page) }
			)
		}
	}
	suspend fun getProductByBarcode(barcode: String): ApiResult<ProductResponse> {
		return withContext(Dispatchers.IO) {
			handleApiResponse(
				request = { apiService.getProductsByBarcode(barcode) }
			)
		}
	}
}