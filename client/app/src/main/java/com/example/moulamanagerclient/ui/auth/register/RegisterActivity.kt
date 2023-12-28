package com.example.moulamanagerclient.ui.auth.register

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
fun RegisterActivity(navController: NavController) {
	val viewModel = hiltViewModel<RegisterViewModel>()
	val inputEmail by viewModel.inputEmail.collectAsState()
	val inputUsername by viewModel.inputUsername.collectAsState()
	val inputPassword by viewModel.inputPassword.collectAsState()
	val inputConfirmPassword by viewModel.inputConfirmPassword.collectAsState()
	val isLoading by viewModel.isLoading.collectAsState()
	val errorMessage by viewModel.errorMessage.collectAsState()

	LaunchedEffect(viewModel.navigateToCart) {
		viewModel.navigateToCart.collect { navigate ->
			if (navigate) {
				navController.navigate(AppRoutes.CART.path) {
					popUpTo(navController.graph.id) {
						inclusive = true
					}
					launchSingleTop = true
				}
			}
		}
	}

	Scaffold(
		topBar = {
			AuthTopBar(R.drawable.logo_full_white)
		}
	) { paddingValues ->
		AuthContainerBorder(
			paddingValues = paddingValues,
			content = {
				RegisterComponent(
					title = "Sign up",
					inputEmail = inputEmail,
					onEmailChange = { viewModel.setEmail(it) },
					inputUsername = inputUsername,
					onUsernameChange = { viewModel.setUsername(it) },
					inputPassword = inputPassword,
					onPasswordChange = { viewModel.setPassword(it) },
					inputConfirmPassword = inputConfirmPassword,
					onConfirmPasswordChange = { viewModel.setConfirmPassword(it) },
					onRegisterClick = { viewModel.performRegister() },
					onLoginClick = {
						navController.navigate(AppRoutes.LOGIN.path) {
							popUpTo(navController.graph.id) {
								inclusive = true
							}
							launchSingleTop = true
						}
					},
					errorMessage = errorMessage,
					isLoading = isLoading,
				)
			}
		)
	}
}