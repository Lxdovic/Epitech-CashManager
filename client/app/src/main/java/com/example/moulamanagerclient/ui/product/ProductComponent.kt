package com.example.moulamanagerclient.ui.product

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.data.model.product.ProductResponse
import com.example.moulamanagerclient.ui.theme.Colors

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
	Box(
		modifier = Modifier
			.fillMaxSize()
			.wrapContentSize(Alignment.Center)
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.size(50.dp)
		)
	}
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
fun ProductList(products: List<ProductResponse>, isNextPageLoading: Boolean, loadMoreProducts: () -> Unit) {
	LazyColumn {
		items(products) { product ->
			ProductComponent(
				name = product.name,
				barcode = product.barcode
			)
		}
		if (isNextPageLoading) {
			item { ProductLoadingComponent() }
		}
		item {
			LaunchedEffect(Unit) {
				loadMoreProducts()
			}
		}
	}
}

@Composable
fun ProductLoadingComponent() {
	Row(
		verticalAlignment = Alignment.CenterVertically,
		horizontalArrangement = Arrangement.Center,
		modifier = Modifier
			.fillMaxWidth()
			.wrapContentSize()
	) {
		CircularProgressIndicator(
			modifier = Modifier
				.size(50.dp)
		)
	}
}