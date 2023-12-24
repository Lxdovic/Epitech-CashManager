package com.example.moulamanagerclient.ui.navbar

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moulamanagerclient.shared.AppRoutes
import com.example.moulamanagerclient.ui.auth.login.LoginActivity
import com.example.moulamanagerclient.ui.product.ProductComponent
import com.example.moulamanagerclient.ui.scan.ScanComponent

@Composable
fun NavigationHost(navigationController: NavHostController) {
	Scaffold(
		bottomBar = { NavbarComponent(navigationController) }
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			NavHost(
				navController = navigationController,
				startDestination = AppRoutes.CART.path
			) {
				composable(AppRoutes.CART.path) { LoginActivity() }
				composable(AppRoutes.SCAN.path) { ScanComponent() }
				composable(AppRoutes.CHECKOUT.path) { ProductComponent() }
				composable(AppRoutes.LOGOUT.path) { ProductComponent() }
			}
		}
	}
}