package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.ui.theme.Colors

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductActivity() {
	val viewModel = hiltViewModel<ProductViewModel>()
	val isLoading by viewModel.isLoading.collectAsState()
	val isNextPageLoading by viewModel.isNextPageLoading.collectAsState()
	val errorMessage by viewModel.errorMessage.collectAsState()
	val products by viewModel.products.collectAsState()

	Scaffold(
		topBar = { ProductTopAppBar() }
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			when {
				isLoading -> ProductLoadingBox()

				!isLoading && products.isEmpty() && errorMessage == null -> ProductEmptyBox("No products available.")

				errorMessage != null -> ProductEmptyBox(errorMessage!!)

				else -> ProductList(products, isNextPageLoading, viewModel::loadMoreProducts)
			}
		}
	}
}