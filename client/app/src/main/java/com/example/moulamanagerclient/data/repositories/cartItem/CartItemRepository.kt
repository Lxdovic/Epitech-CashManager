package com.example.moulamanagerclient.data.repositories.cartItem

import com.example.moulamanagerclient.data.model.cartItem.AddProductToCartRequest
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiHelper
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CartItemRepository
@Inject
constructor(
    private val apiService: ApiService
) {
    suspend fun addProduct(product: AddProductToCartRequest, barcode: String): ApiResult<ProductResponse> {
        return withContext(Dispatchers.IO) {
            ApiHelper.handleApiResponse(
                request = { apiService.addProductToCartItem(product, barcode) }
            )
        }
    }
}