package com.example.moulamanagerclient.ui.product

import androidx.lifecycle.ViewModel
import com.example.moulamanagerclient.data.model.Pagination
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.repositories.products.ProductRepository
import com.example.moulamanagerclient.shared.AppConstants
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
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

	private val _isSearching: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val isSearching: StateFlow<Boolean> = _isSearching

	private val _products: MutableStateFlow<List<ProductResponse>> = MutableStateFlow(emptyList())
	val products: StateFlow<List<ProductResponse>> = _products

	private val _errorMessage: MutableStateFlow<String?> = MutableStateFlow(null)
	val errorMessage: StateFlow<String?> = _errorMessage

	private val _hasMoreProducts: MutableStateFlow<Boolean> = MutableStateFlow(true)
	val hasMoreProducts: StateFlow<Boolean> = _hasMoreProducts

	private val viewModelJob = Job()
	private val viewModelScope = CoroutineScope(Dispatchers.Main + viewModelJob)

	private val _query = MutableStateFlow("")
	val query: StateFlow<String> get() = _query

	private var currentPage = 0
	private var hasMorePages = true


	init {
		fetchProducts()
	}

	/**
	 * Cancel the job when the ViewModel is destroyed
	 */
	override fun onCleared() {
		super.onCleared()
		viewModelJob.cancel()
	}

	fun searchProducts(query: String = "") = viewModelScope.launch {
		_isSearching.value = true
		// If the user clears the search bar, we reset the state of the list and fetch the products
		if (query.isBlank()) {
			resetState()
			getProducts()
			return@launch
		}

		resetState()

		val result = productRepository.searchProductsByName(query, currentPage, AppConstants.DEFAULT_PAGE_SIZE)
		handleApiResult(result)

		_isLoading.value = false
		_isSearching.value = false
	}

	fun loadMoreProducts() = viewModelScope.launch {
		if (_hasMoreProducts.value && !_isNextPageLoading.value) {
			_isNextPageLoading.value = true
			getProducts()
			_isNextPageLoading.value = false

		}
	}

	private fun fetchProducts() = viewModelScope.launch {
		getProducts()
	}


	private suspend fun getProducts() {
		if (!hasMorePages) {
			_hasMoreProducts.value = false
			return
		}

		val result = productRepository.getProducts(currentPage, AppConstants.DEFAULT_PAGE_SIZE)
		handleApiResult(result)

		_isLoading.value = false
	}

	/**
	 * Reset the state of the list
	 * This is useful when we want to search for a new query
	 */
	private fun resetState() {
		currentPage = 0
		hasMorePages = true
		_hasMoreProducts.value = true
		_isLoading.value = true
		_products.value = emptyList()
	}

	/**
	 * Handle the result of the API call and update the state of the list
	 */
	private fun handleApiResult(result: ApiResult<Pagination<ProductResponse>>) {
		when (result) {
			is ApiResult.Success -> {
				_products.value += result.data?.content ?: emptyList()
				currentPage++
				hasMorePages = !result.data?.last!!
			}

			is ApiResult.Error -> {
				_errorMessage.value = "An error occurred: ${result.errorInfo.message}"
			}

			ApiResult.Initial -> {}
		}
	}
}