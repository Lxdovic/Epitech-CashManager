package com.example.moulamanagerclient.ui.auth.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.R

@Composable
fun InputField(
	label: String,
	value: String,
	onValueChange: (String) -> Unit,
	isPassword: Boolean = false
) {
	val (showPassword, setShowPassword) = remember { mutableStateOf(false) }

	OutlinedTextField(
		colors = OutlinedTextFieldDefaults.colors(
			focusedBorderColor = colorResource(id = R.color.black_4),
			unfocusedBorderColor = colorResource(id = R.color.black_4),
			focusedTextColor = colorResource(id = R.color.white),
			unfocusedTextColor = colorResource(id = R.color.white),
		),
		modifier = Modifier
			.fillMaxWidth()
			.padding(bottom = 10.dp),
		value = value,
		onValueChange = onValueChange,
		label = { Text(text = label) },
		visualTransformation = if (isPassword && !showPassword) PasswordVisualTransformation() else VisualTransformation.None,
		trailingIcon = {
			if (isPassword) {
				IconButton(onClick = { setShowPassword(!showPassword) }) {
					Icon(
						imageVector = if (showPassword) Icons.Filled.Visibility else Icons.Filled.VisibilityOff,
						contentDescription = if (showPassword) "Hide password" else "Show password"
					)
				}
			}
		}
	)
}