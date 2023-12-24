package com.example.moulamanagerclient.data.repositories.products

import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.utils.Retrofit
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class ProductRepository {
    suspend fun getProductByBarcode(barcode: String): ProductResponse? {
        try {
            val response = CoroutineScope(Dispatchers.IO).async {
                Retrofit.apiService.getProducts(barcode).body()
            }.await()

            return response?.let {
                ProductResponse(
                    id = it.id,
                    name = it.name,
                    price = it.price,
                    barcode = it.barcode,
                    description = it.description
                )
            }
        } catch (e: Exception) {
            throw e
        }
    }
}