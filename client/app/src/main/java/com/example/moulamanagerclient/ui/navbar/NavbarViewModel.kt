package com.example.moulamanagerclient.ui.navbar

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.mutableIntStateOf
import androidx.lifecycle.ViewModel
import com.example.moulamanagerclient.data.model.nav.NavbarItem
import com.example.moulamanagerclient.shared.AppRoutes

class NavbarViewModel: ViewModel() {
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

    val selectedItemIndex = mutableIntStateOf(0)
}