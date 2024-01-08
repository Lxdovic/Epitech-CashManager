package com.example.moulamanagerclient.ui.navbar

import ScanComponent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.moulamanagerclient.data.network.AuthInterceptor
import com.example.moulamanagerclient.shared.AppRoutes
import com.example.moulamanagerclient.ui.auth.login.LoginActivity
import com.example.moulamanagerclient.ui.auth.register.RegisterActivity
import com.example.moulamanagerclient.ui.product.ProductActivity

@Composable
fun NavigationHost(navigationController: NavHostController, authInterceptor: AuthInterceptor) {
	val startDestination = if (authInterceptor.isLoggedIn()) AppRoutes.CART.path else AppRoutes.LOGIN.path

	Scaffold(
		bottomBar = {
			val currentRoute = navigationController.currentBackStackEntryAsState().value?.destination?.route
			if (currentRoute in listOf(
					AppRoutes.CART.path,
					AppRoutes.SCAN.path,
					AppRoutes.PRODUCT.path,
					AppRoutes.LOGOUT.path
				)
			) {
				NavbarComponent(navigationController)
			}
		}
	) { paddingValues ->
		Column(Modifier.padding(paddingValues)) {
			NavHost(
				navController = navigationController,
				startDestination = startDestination
			) {
				composable(AppRoutes.LOGIN.path) { LoginActivity(navigationController) }
				composable(AppRoutes.REGISTER.path) { RegisterActivity(navigationController) }
				composable(AppRoutes.CART.path) { ProductActivity() }
				composable(AppRoutes.SCAN.path) { ScanComponent() }
				composable(AppRoutes.PRODUCT.path) { ProductActivity() }
				composable(AppRoutes.LOGOUT.path) { ProductActivity() }
			}
		}
	}
}