package com.example.moulamanagerclient.ui.component

import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun TitleSubtitleLayoutShimmer() {
	LazyColumn {
		items(10) {
			Card(
				modifier = Modifier
					.fillMaxWidth()
					.padding(8.dp),
				shape = RoundedCornerShape(8.dp)
			) {
				Column(
					modifier = Modifier
						.padding(8.dp)
				) {
					Box(
						modifier = Modifier
							.fillMaxWidth(0.6f)
							.height(24.dp)
							.background(brush = shimmerBrush())
					)
					Spacer(modifier = Modifier.height(8.dp))
					Box(
						modifier = Modifier
							.fillMaxWidth()
							.height(24.dp)
							.background(brush = shimmerBrush())
					)
				}
			}
		}
	}
}


@Composable
fun shimmerBrush(showShimmer: Boolean = true, targetValue: Float = 1000f): Brush {
	return if (showShimmer) {
		val shimmerColors = listOf(
			Color.LightGray.copy(alpha = 0.6f),
			Color.LightGray.copy(alpha = 0.2f),
			Color.LightGray.copy(alpha = 0.6f),
		)

		val transition = rememberInfiniteTransition()
		val translateAnimation = transition.animateFloat(
			initialValue = 0f,
			targetValue = targetValue,
			animationSpec = infiniteRepeatable(
				animation = tween(800), repeatMode = RepeatMode.Reverse
			)
		)
		Brush.linearGradient(
			colors = shimmerColors,
			start = Offset.Zero,
			end = Offset(x = translateAnimation.value, y = translateAnimation.value)
		)
	} else {
		Brush.linearGradient(
			colors = listOf(Color.Transparent, Color.Transparent),
			start = Offset.Zero,
			end = Offset.Zero
		)
	}
}