package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.ui.component.TitleSubtitleLayoutShimmer
import com.example.moulamanagerclient.ui.theme.Colors
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

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
fun ProductComponent(name: String, description: String, backgroundColor: Color) {
	Surface(
		modifier = Modifier
			.fillMaxSize()
			.height(75.dp),
		color = backgroundColor
	) {
		Column(
			modifier = Modifier
				.fillMaxSize()
				.padding(16.dp),
		) {

			Text(
				text = name,
				color = Colors.WHITE,
			)
			Text(
				text = description,
				maxLines = 1,
				overflow = TextOverflow.Ellipsis,
				color = Colors.WHITE,
			)
		}
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
		itemsIndexed(products) { index, product ->
			ProductComponent(
				name = product.name,
				description = product.description,
				backgroundColor = if (index % 2 == 0) Colors.BLACK_1 else Colors.BLACK_2
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
			color = MaterialTheme.colorScheme.secondary

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
			colors = TextFieldDefaults.colors(
				unfocusedContainerColor = MaterialTheme.colorScheme.tertiary,
				focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
				unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
				focusedLabelColor = MaterialTheme.colorScheme.secondary,
				unfocusedLabelColor = MaterialTheme.colorScheme.secondary,
				cursorColor = MaterialTheme.colorScheme.secondary,
				focusedContainerColor = MaterialTheme.colorScheme.tertiary,
				focusedTextColor = Colors.WHITE,
			),
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
			label = { Text(text = "Search") },
			singleLine = true,
			modifier = Modifier.fillMaxWidth(),
			trailingIcon = {
				if (query.value.isEmpty()) {
					Icon(
						imageVector = Icons.Default.Search,
						contentDescription = "Search",
						tint = Colors.YELLOW_1
					)
				} else {
					IconButton(onClick = {
						query.value = ""
						onSearch("")
					}) {
						Icon(
							imageVector = Icons.Default.Clear,
							contentDescription = "Clear search",
							tint = Colors.YELLOW_1
						)
					}
				}
			}
		)
	}
}