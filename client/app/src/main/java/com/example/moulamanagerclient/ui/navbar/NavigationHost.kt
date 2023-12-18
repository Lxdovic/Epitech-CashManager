package com.example.moulamanagerclient.ui.navbar

import ScanComponent
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
import com.example.moulamanagerclient.ui.cart.CartComponent
import com.example.moulamanagerclient.ui.product.ProductComponent

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(navigationController: NavHostController) {
    Scaffold(
        bottomBar = { NavbarComponent(navigationController) }
    ) { paddingValues ->
        Column(Modifier.padding(paddingValues)) {
            NavHost(navController = navigationController, startDestination = AppRoutes.cart.path) {
                composable(AppRoutes.cart.path) { CartComponent() }
                composable(AppRoutes.scan.path) { ScanComponent() }
                composable(AppRoutes.checkout.path) { ProductComponent() }
                composable(AppRoutes.logout.path) { ProductComponent() }

            }
        }
    }
}