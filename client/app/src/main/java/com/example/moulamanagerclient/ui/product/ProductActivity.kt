package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel

@Composable
fun ProductActivity() {
	val viewModel = hiltViewModel<ProductViewModel>()
	val isLoading by viewModel.isLoading.collectAsState()
	val isNextPageLoading by viewModel.isNextPageLoading.collectAsState()
	val hasMoreProducts by viewModel.hasMoreProducts.collectAsState()
	val errorMessage by viewModel.errorMessage.collectAsState()
	val products by viewModel.products.collectAsState()

	Scaffold(
		topBar = { ProductTopAppBar() },
		bottomBar = {
			ProductSearchBar(
				query = remember { mutableStateOf(viewModel.query.value) },
				onSearch = viewModel::searchProducts
			)
		}
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			when {
				isLoading -> ProductLoadingBox()

				!isLoading && products.isEmpty() && errorMessage == null -> ProductEmptyBox("No products available.")

				errorMessage != null -> ProductEmptyBox(errorMessage!!)

				else -> ProductList(products, isNextPageLoading, hasMoreProducts, viewModel::loadMoreProducts)
			}
		}
	}
}