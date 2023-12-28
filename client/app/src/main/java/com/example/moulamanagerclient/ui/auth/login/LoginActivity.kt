package com.example.moulamanagerclient.ui.auth.login

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.shared.AppRoutes
import com.example.moulamanagerclient.ui.auth.composables.AuthContainerBorder
import com.example.moulamanagerclient.ui.auth.composables.AuthTopBar

@Composable
fun LoginActivity(navController: NavController) {
	val viewModel = hiltViewModel<LoginViewModel>()
	val inputPassword by viewModel.inputPassword.collectAsState()
	val inputUsername by viewModel.inputUsername.collectAsState()
	val errorMessage by viewModel.errorMessage.collectAsState()
	val isLoading by viewModel.isLoading.collectAsState()

	LaunchedEffect(viewModel.navigateToCart) {
		viewModel.navigateToCart.collect {
			navController.navigate(AppRoutes.CART.path) {
				popUpTo(navController.graph.id) {
					inclusive = true
				}
				launchSingleTop = true
			}
		}
	}

	LaunchedEffect(viewModel.navigateToRegister) {
		viewModel.navigateToRegister.collect {
			navController.navigate(AppRoutes.REGISTER.path) {
				popUpTo(navController.graph.id) {
					inclusive = true
				}
				launchSingleTop = true
			}
		}
	}

	Scaffold(
		topBar = {
			AuthTopBar(logo = R.drawable.logo_full_white)
		}
	) { paddingValues ->
		AuthContainerBorder(
			paddingValues = paddingValues,
			content = {
				LoginComponent(
					title = "Sign in",
					inputUsername = inputUsername,
					onUsernameChange = { viewModel.setUsername(it) },
					inputPassword = inputPassword,
					onPasswordChange = { viewModel.setPassword(it) },
					onLoginClick = { viewModel.performLogin() },
					onRegisterClick = { viewModel.navigateToRegister() },
					errorMessage = errorMessage,
					isLoading = isLoading,
				)
			}

		)
	}
}