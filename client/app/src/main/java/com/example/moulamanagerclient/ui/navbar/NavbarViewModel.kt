package com.example.moulamanagerclient.ui.home

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.*
import androidx.lifecycle.ViewModel
import com.example.moulamanagerclient.data.model.nav.NavbarItem

class NavbarViewModel: ViewModel() {
    val items = listOf(
        NavbarItem(
            title = "Cart",
            route = "cart",
            selectedIcon = Icons.Filled.ShoppingCart,
            unselectedIcon = Icons.Outlined.ShoppingCart,
            hasNews = false,
        ),

        NavbarItem(
            title = "Scan",
            route = "scan",
            selectedIcon = Icons.Filled.Scanner,
            unselectedIcon = Icons.Outlined.Scanner,
            hasNews = false,
        ),

        NavbarItem(
            title = "Checkout",
            route = "checkout",
            selectedIcon = Icons.Filled.CreditCard,
            unselectedIcon = Icons.Outlined.CreditCard,
            hasNews = false,
        ),

        NavbarItem(
            title = "Logout",
            route = "logout",
            selectedIcon = Icons.Filled.Logout,
            unselectedIcon = Icons.Outlined.Logout,
            hasNews = false,
        ),
    )
}