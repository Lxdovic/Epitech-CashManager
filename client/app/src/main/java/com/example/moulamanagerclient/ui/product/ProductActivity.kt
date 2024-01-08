package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moulamanagerclient.ui.theme.Colors

@Composable
fun ProductActivity() {
	val viewModel = hiltViewModel<ProductViewModel>()
	val isLoading by viewModel.isLoading.collectAsState()
	val isSearching by viewModel.isSearching.collectAsState()
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
		Surface(
			modifier = Modifier
				.fillMaxSize(),
			color = Colors.BLACK_1
		) {
			Column(Modifier.padding(paddingValues)) {
				when {
					isLoading && !isSearching -> ProductLoadingBox()
					isLoading && isSearching -> ProductLoadingComponent()

					products.isEmpty() && !isSearching && errorMessage == null -> ProductEmptyBox("No products found")

					errorMessage != null -> ProductEmptyBox(errorMessage!!)

					else -> ProductList(products, isNextPageLoading, hasMoreProducts, viewModel::loadMoreProducts)
				}
			}
		}
	}
}