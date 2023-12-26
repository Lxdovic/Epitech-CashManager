package com.example.moulamanagerclient.data.repositories.products

import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiHelper.handleApiResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ProductRepository
@Inject
constructor(
	private val apiService: ApiService
) {
	private val productCache = mutableMapOf<String, Pagination<ProductResponse>>()

	suspend fun getProducts(page: Int, size: Int): ApiResult<Pagination<ProductResponse>> {
		return withContext(Dispatchers.IO) {
			val cacheKey = "products_page_$page"
			val cachedProduct = productCache[cacheKey]
			if (cachedProduct != null) {
				return@withContext ApiResult.Success(cachedProduct)
			}

			val result = handleApiResponse(
				request = { apiService.getProducts(page, size) }
			)

			if (result is ApiResult.Success && result.data != null) {
				productCache[cacheKey] = result.data
			}

			result
		}
	}

	suspend fun searchProductsByName(name: String, page: Int, size: Int): ApiResult<Pagination<ProductResponse>> {
		return withContext(Dispatchers.IO) {
			val cacheKey = "search_${name}_page_$page"
			val cachedProduct = productCache[cacheKey]
			if (cachedProduct != null) {
				return@withContext ApiResult.Success(cachedProduct)
			}

			val result = handleApiResponse(
				request = { apiService.searchProductsByName(name, page, size) }
			)

			if (result is ApiResult.Success && result.data != null) {
				productCache[cacheKey] = result.data
			}

			result
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