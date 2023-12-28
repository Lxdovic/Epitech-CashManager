package com.example.moulamanagerclient.ui.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material.icons.filled.ShoppingCart
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Scanner
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moulamanagerclient.data.model.nav.NavbarItem
import com.example.moulamanagerclient.data.network.AuthInterceptor
import com.example.moulamanagerclient.shared.AppRoutes
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NavbarViewModel
@Inject
constructor(
	private val authInterceptor: AuthInterceptor
) : ViewModel() {
	val selectedItemIndex = mutableIntStateOf(0)

	val items = listOf(
		NavbarItem(
			title = AppRoutes.CART.title,
			route = AppRoutes.CART.path,
			selectedIcon = Icons.Filled.ShoppingCart,
			unselectedIcon = Icons.Outlined.ShoppingCart
		),

		NavbarItem(
			title = AppRoutes.SCAN.title,
			route = AppRoutes.SCAN.path,
			selectedIcon = Icons.Filled.Scanner,
			unselectedIcon = Icons.Outlined.Scanner
		),

		NavbarItem(
			title = AppRoutes.PRODUCT.title,
			route = AppRoutes.PRODUCT.path,
			selectedIcon = Icons.Filled.CreditCard,
			unselectedIcon = Icons.Outlined.CreditCard
		),

		NavbarItem(
			title = AppRoutes.LOGOUT.title,
			route = AppRoutes.LOGOUT.path,
			selectedIcon = Icons.Filled.Logout,
			unselectedIcon = Icons.Outlined.Logout
		),

		)

	fun onLogoutClick(navController: NavController) {
		authInterceptor.logout()
		navController.navigate(AppRoutes.LOGIN.path) {
			popUpTo(navController.graph.id) {
				inclusive = true
			}
		}
	}
}