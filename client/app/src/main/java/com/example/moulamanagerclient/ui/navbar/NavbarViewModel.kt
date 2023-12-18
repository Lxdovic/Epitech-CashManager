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
            title = AppRoutes.cart.title,
            route = AppRoutes.cart.path,
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart
        ),

        NavbarItem(
            title = AppRoutes.scan.title,
            route = AppRoutes.scan.path,
            selectedIcon = Icons.Filled.Scanner,
            unselectedIcon = Icons.Outlined.Scanner
        ),

        NavbarItem(
            title = AppRoutes.checkout.title,
            route = AppRoutes.checkout.path,
            selectedIcon = Icons.Filled.CreditCard,
            unselectedIcon = Icons.Outlined.CreditCard
        ),

        NavbarItem(
            title = AppRoutes.logout.title,
            route = AppRoutes.logout.path,
            selectedIcon = Icons.Filled.Logout,
            unselectedIcon = Icons.Outlined.Logout
        ),

    )

    val selectedItemIndex = mutableIntStateOf(0)
}