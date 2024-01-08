package com.example.moulamanagerclient.ui.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moulamanagerclient.data.model.cartItem.AddProductToCartRequest
import com.example.moulamanagerclient.data.model.product.CreateProductRequest
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.repositories.cartItem.CartItemRepository
import com.example.moulamanagerclient.data.repositories.products.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val cartItemRepository: CartItemRepository
) : ViewModel() {

    private val _productResult: MutableStateFlow<ProductResponse?> = MutableStateFlow(null)
    private val _createProductResult: MutableStateFlow<ProductResponse?> = MutableStateFlow(null)
    private val _ean: MutableStateFlow<String> = MutableStateFlow("")
    private val _amount: MutableStateFlow<String> = MutableStateFlow("1")
    private val _newProductName: MutableStateFlow<String> = MutableStateFlow("")
    private val _newProductDescription: MutableStateFlow<String> = MutableStateFlow("")
    private val _newProductPrice: MutableStateFlow<String> = MutableStateFlow("0")
    val ean: StateFlow<String> = _ean
    val amount: StateFlow<String> = _amount
    val productResult: StateFlow<ProductResponse?> = _productResult
    val createProductResult: StateFlow<ProductResponse?> = _createProductResult
    val newProductName: StateFlow<String> = _newProductName
    val newProductPrice: StateFlow<String> = _newProductPrice
    val newProductDescription: StateFlow<String> = _newProductDescription


    fun setEan(ean: String) {
        _ean.value = ean
    }

    fun getEan(): String {
        return _ean.value
    }

    fun setAmount(amount: String) {
        _amount.value = amount
    }

    fun setNewProductPrice(price: String) {
        _newProductPrice.value = price
    }

    fun setNewProductName(name: String) {
        _newProductName.value = name
    }

    fun setNewProductDescription(name: String) {
        _newProductDescription.value = name
    }

    fun reset() {
        _ean.value = ""
        _amount.value = "1"
        _newProductName.value = ""
        _newProductDescription.value = ""
        _newProductPrice.value = "0"
        _productResult.value = null
        _createProductResult.value = null
    }

    fun getProduct(barcode: String) {
        viewModelScope.launch {
            val result = productRepository.getProductByBarcode(barcode)

            when (result) {
                is ApiResult.Success -> {
                    _productResult.value = result.data
                }

                is ApiResult.Error -> {
                    Log.d("EAN", "Error")
                }

                ApiResult.Initial -> {}
            }
        }
    }

    fun createProduct(name: String, price: String, description: String, barcode: String) {
        viewModelScope.launch {
            Log.d("EAN", barcode)
            val request =
                CreateProductRequest(
                    name = name,
                    description = description,
                    price = price.toFloat(),
                    barcode = barcode
                )

            val result = productRepository.createProduct(request)

            when (result) {
                is ApiResult.Success -> {
                    _createProductResult.value = result.data
                }

                is ApiResult.Error -> {
                    Log.d("EAN", "Error")
                }

                ApiResult.Initial -> {}
            }
        }
    }

    fun addProductToCart(barcode: String, quantity: String) {
        viewModelScope.launch {
            val request = AddProductToCartRequest(quantity = quantity.toInt())
            val response = cartItemRepository.addProduct(request, barcode)

//            withContext(Dispatchers.Main) {
//                _createProductResult.value = response
//            }
        }
    }

}