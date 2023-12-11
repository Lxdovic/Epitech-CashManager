package com.example.moulamanagerclient.ui.navbar

import CheckoutComponent
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
import com.example.moulamanagerclient.ui.cart.CartComponent
import com.example.moulamanagerclient.ui.home.NavbarComponent


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NavigationHost(navigationController: NavHostController) {
    Scaffold(
    bottomBar = {
        NavbarComponent(navigationController)
    }
) {
    Column(Modifier.padding(it)) {
        NavHost(navController = navigationController, startDestination = "cart") {
//            composable("logout") {
//                LoginActivity(navigationController)
//            }

            composable("cart") {
                CartComponent(navigationController)
            }

            composable("scan") {
                ScanComponent(navigationController)
            }

            composable("checkout") {
                CheckoutComponent(navigationController)
            }
        }
    }
}

}