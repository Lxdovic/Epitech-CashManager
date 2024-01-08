package com.example.moulamanagerclient.ui.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CreditCard
import androidx.compose.material.icons.filled.Logout
import androidx.compose.material.icons.filled.Scanner
import androidx.compose.material.icons.outlined.CreditCard
import androidx.compose.material.icons.outlined.Logout
import androidx.compose.material.icons.outlined.Scanner
import androidx.compose.material.icons.outlined.ShoppingCart
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.ui.res.painterResource
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.example.moulamanagerclient.R
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
			selectedIcon = R.drawable.selected_cart,
			unselectedIcon = R.drawable.unselected_cart
		),

		NavbarItem(
			title = AppRoutes.SCAN.title,
			route = AppRoutes.SCAN.path,
			selectedIcon = R.drawable.selected_scan,
			unselectedIcon = R.drawable.unselected_scan
		),

		NavbarItem(
			title = AppRoutes.PRODUCT.title,
			route = AppRoutes.PRODUCT.path,
			selectedIcon = R.drawable.selected_list,
			unselectedIcon = R.drawable.unselected_list
		),

		NavbarItem(
			title = AppRoutes.LOGOUT.title,
			route = AppRoutes.LOGOUT.path,
			selectedIcon = R.drawable.logout_icon,
			unselectedIcon = R.drawable.logout_icon
		),

		)

	fun onLogoutClick(navController: NavController) {
		authInterceptor.logout()
		selectedItemIndex.intValue = 0
		navController.navigate(AppRoutes.LOGIN.path) {
			popUpTo(navController.graph.id) {
				inclusive = true
			}
		}
	}
}