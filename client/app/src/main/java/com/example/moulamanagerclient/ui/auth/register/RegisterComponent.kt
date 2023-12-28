package com.example.moulamanagerclient.ui.auth.register

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.ui.auth.composables.*

@Composable
fun RegisterComponent(
	title: String,
	inputEmail: String,
	onEmailChange: (String) -> Unit,
	inputUsername: String,
	onUsernameChange: (String) -> Unit,
	inputPassword: String,
	onPasswordChange: (String) -> Unit,
	inputConfirmPassword: String,
	onConfirmPasswordChange: (String) -> Unit,
	onRegisterClick: () -> Unit,
	onLoginClick: () -> Unit,
	errorMessage: String,
	isLoading: Boolean,
) {
	AuthContainer {
		AuthTitle(title = title)
		Spacer(modifier = Modifier.height(10.dp))
		InputField(
			label = "Email",
			value = inputEmail,
			onValueChange = onEmailChange
		)
		InputField(
			label = "Username",
			value = inputUsername,
			onValueChange = onUsernameChange
		)
		InputField(
			label = "Password",
			value = inputPassword,
			onValueChange = onPasswordChange,
			isPassword = true
		)
		InputField(
			label = "Confirm password",
			value = inputConfirmPassword,
			onValueChange = onConfirmPasswordChange,
			isPassword = true
		)
		if (errorMessage.isNotEmpty()) {
			Text(
				text = errorMessage,
				color = Color.Red,
				style = MaterialTheme.typography.bodyMedium
			)
		}
		AuthButton(
			onClick = onRegisterClick,
			label = "Sign Up",
			isLoading = isLoading,
		)

		DividerWithText(text = "OR")

		AuthButton(
			onClick = onLoginClick,
			label = "Sign in",
			isLoading = false,
			borderColor = Color.Gray
		)
	}
}
