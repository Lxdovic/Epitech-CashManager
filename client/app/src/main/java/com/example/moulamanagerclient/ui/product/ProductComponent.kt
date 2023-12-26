package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.ui.component.TitleSubtitleLayoutShimmer
import com.example.moulamanagerclient.ui.theme.Colors
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun ProductComponent(name: String, barcode: String) {
	Text(text = name)
	Text(text = barcode)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProductTopAppBar() {
	TopAppBar(
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = Colors.BLACK_0,
			titleContentColor = Colors.WHITE,
		),
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentSize()
			) {
				Image(
					painter = painterResource(id = R.drawable.logo_full_white),
					contentDescription = "Logo",
				)
			}
		}
	)
}

@Composable
fun ProductLoadingBox() {
	TitleSubtitleLayoutShimmer()
}

@Composable
fun ProductEmptyBox(message: String) {
	Box(
		modifier = Modifier
			.fillMaxSize()
			.wrapContentSize(Alignment.Center)
	) {
		Text(text = message)
	}
}

@Composable
fun ProductList(
	products: List<ProductResponse>,
	isNextPageLoading: Boolean,
	hasMoreProducts: Boolean,
	loadMoreProducts: () -> Unit
) {
	LazyColumn {
		items(products) { product ->
			ProductComponent(
				name = product.name,
				barcode = product.barcode
			)
		}
		if (isNextPageLoading && hasMoreProducts) {
			item { ProductLoadingComponent() }
		}
		if (products.isNotEmpty() && hasMoreProducts && !isNextPageLoading) {
			item {
				LaunchedEffect(Unit) {
					loadMoreProducts()
				}
			}
		}
	}
}

/**
 * Loading component for the list of products
 */
@Composable
fun ProductLoadingComponent() {
	Box(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentSize(Alignment.Center)
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.size(48.dp),
			color = Colors.YELLOW_1

		)
	}
}

/**
 * Search bar for products
 *
 * TODO: Create a custom design for the search bar
 * @param query The query to search for
 * @param onSearch The callback to execute when the user types in the search bar
 * @see ProductViewModel.searchProducts
 */
@Composable
fun ProductSearchBar(
	query: MutableState<String>,
	onSearch: (String) -> Unit
) {
	val scope = rememberCoroutineScope()
	var job by remember { mutableStateOf<Job?>(null) }

	Row(
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentSize()
	) {
		TextField(
			value = query.value,
			onValueChange = { newValue ->
				query.value = newValue
				job?.cancel()
				if (newValue.length >= 3) {
					job = scope.launch {
						// Debounce time after user stops typing
						delay(300)
						onSearch(newValue)
					}
				} else if (newValue.isEmpty()) {
					job = scope.launch {
						// Debounce time after user stops typing
						delay(300)
						onSearch("")
					}
				}
			},
			label = { Text("Search") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth()
		)
	}
}