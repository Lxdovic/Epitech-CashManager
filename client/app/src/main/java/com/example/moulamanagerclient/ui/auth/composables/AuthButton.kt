package com.example.moulamanagerclient.ui.auth.composables

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.moulamanagerclient.ui.theme.Colors

@Composable
fun AuthButton(
	onClick: () -> Unit,
	label: String,
	isLoading: Boolean,
	borderColor: Color = Colors.YELLOW_1,
) {
	OutlinedButton(
		shape = RoundedCornerShape(10),
		border = BorderStroke(1.dp, borderColor),
		onClick = onClick,
		enabled = !isLoading,
		modifier = Modifier.fillMaxWidth()
	) {
		Box(contentAlignment = Alignment.Center) {
			if (isLoading) {
				CircularProgressIndicator(
					color = borderColor,
					modifier = Modifier.size(20.dp)
				)
			} else {
				Text(
					text = label,
					color = Colors.WHITE,
				)
			}
		}
	}
}