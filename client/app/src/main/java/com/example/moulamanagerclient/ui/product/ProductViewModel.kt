package com.example.moulamanagerclient.ui.product

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.repositories.products.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductViewModel
@Inject
constructor(
	private val productRepository: ProductRepository
) : ViewModel() {

	private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(true)
	val isLoading: StateFlow<Boolean> = _isLoading

	private val _isNextPageLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val isNextPageLoading: StateFlow<Boolean> = _isNextPageLoading

	private val _products: MutableStateFlow<List<ProductResponse>> = MutableStateFlow(emptyList())
	val products: StateFlow<List<ProductResponse>> = _products

	private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
	val errorMessage: StateFlow<String?> = _errorMessage

	private var currentPage = 0
	private var hasMorePages = true

	init {
		fetchProducts()
	}

	private fun fetchProducts() = viewModelScope.launch {
		getProducts()
	}

	private suspend fun getProducts() {
		if (!hasMorePages) return

		_isNextPageLoading.value = true

		when (val result = productRepository.getProducts(currentPage)) {
			is ApiResult.Success -> {
				_products.value += result.data?.content ?: emptyList()
				currentPage++
				hasMorePages = !result.data?.last!!
			}

			is ApiResult.Error -> {
				_errorMessage.value = "An error occurred: ${result.errorInfo.message}"
			}

			ApiResult.Initial -> {
				_isNextPageLoading.value = false
			}

		}

		_isNextPageLoading.value = false
		_isLoading.value = false
	}

	fun loadMoreProducts() = viewModelScope.launch {
		_isNextPageLoading.value = true
		getProducts()
	}

}