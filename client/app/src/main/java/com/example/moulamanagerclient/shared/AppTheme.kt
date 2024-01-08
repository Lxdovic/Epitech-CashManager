package com.example.moulamanagerclient.shared

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import com.example.moulamanagerclient.ui.theme.Colors

val LightColorPalette = lightColorScheme(
	// TODO: Set your light colors here
)

val DarkColorPalette = darkColorScheme(
	primaryContainer = Colors.BLACK_4,
	background = Colors.BLACK_1,
	primary = Colors.BLACK_1,
	secondary = Colors.YELLOW_1,
	tertiary = Colors.BLACK_0,
	onPrimary = Colors.WHITE,
	error = Colors.RED,
)

@Composable
fun MyAppTheme(content: @Composable () -> Unit) {
	val colors = if (isSystemInDarkTheme()) DarkColorPalette else DarkColorPalette // TODO: Change this to LightColorPalette when you want to use light theme

	MaterialTheme(
		colorScheme = colors,
		content = content
	)
}