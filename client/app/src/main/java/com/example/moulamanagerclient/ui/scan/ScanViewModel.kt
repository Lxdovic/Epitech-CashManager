package com.example.moulamanagerclient.ui.scan

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.data.repositories.products.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class ScanViewModel @Inject constructor(
	private val productRepository: ProductRepository
) : ViewModel() {

	private val _productResult: MutableStateFlow<ProductResponse?> = MutableStateFlow(null)
	private val _ean: MutableStateFlow<String> = MutableStateFlow("")
	private val _amount: MutableStateFlow<String> = MutableStateFlow("1")
	val ean: StateFlow<String> = _ean
	val amount: StateFlow<String> = _amount
	val productResult: StateFlow<ProductResponse?> = _productResult

	fun setEan(ean: String) {
		_ean.value = ean
	}

	fun setAmount(amount: String) {
		_amount.value = amount
	}

	fun getAmount(): String {
		return _amount.value
	}

	fun reset() {
		_ean.value = ""
		_amount.value = "1"
	}

	fun getProduct(barcode: String) {

		viewModelScope.launch {
			val response = productRepository.getProductByBarcode(barcode)

			withContext(Dispatchers.Main) {
				_productResult.value = response
				Log.d("Product", response.toString())
			}
		}
	}

}