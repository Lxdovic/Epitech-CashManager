package com.example.moulamanagerclient.ui.auth.composables

import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moulamanagerclient.R
import com.example.moulamanagerclient.ui.theme.Colors
import androidx.compose.material3.Text

@Composable
fun AuthTitle(title: String) {
	Text(
		text = title,
		color = Color.White,
		style = MaterialTheme.typography.titleLarge.copy(
			fontSize = 36.sp,
			fontWeight = FontWeight(700)
		),
		modifier = Modifier
			.drawBehind {
				val strokeWidthPx = 1.dp.toPx()
				val verticalOffset = size.height - strokeWidthPx / 2

				drawLine(
					color = Colors.YELLOW_1,
					strokeWidth = strokeWidthPx,
					start = Offset(0f, verticalOffset),
					end = Offset(size.width, verticalOffset)
				)
			}
	)
}

@Composable
fun DividerWithText(text: String) {
	Row(
		modifier = Modifier.fillMaxWidth(),
		horizontalArrangement = Arrangement.Center,
		verticalAlignment = Alignment.CenterVertically
	) {
		Divider(
			color = Color.Gray,
			modifier = Modifier
				.weight(1f)
				.padding(end = 8.dp)
		)
		Text(text = text, color = Color.Gray, style = MaterialTheme.typography.bodyMedium)
		Divider(
			color = Color.Gray,
			modifier = Modifier
				.weight(1f)
				.padding(start = 8.dp)
		)
	}
}

@Composable
fun AuthContainer(content: @Composable () -> Unit) {
	Column(
		horizontalAlignment = Alignment.Start,
		verticalArrangement = Arrangement.Top,
		modifier = Modifier
			.wrapContentSize()
			.border(width = 1.dp, color = colorResource(R.color.black_3), shape = RoundedCornerShape(2))
			.padding(20.dp)
	) {
		content()
	}
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthTopBar(logo: Int) {
	TopAppBar(
		colors = TopAppBarDefaults.topAppBarColors(
			containerColor = Colors.BLACK_0,
			titleContentColor = Colors.WHITE,
		),
		title = {
			Row(
				verticalAlignment = Alignment.CenterVertically,
				horizontalArrangement = Arrangement.Center,
				modifier = Modifier
					.fillMaxWidth()
					.wrapContentSize()
			) {
				Image(
					painter = painterResource(id = logo),
					contentDescription = "Logo",
				)
			}
		}
	)
}

@Composable
fun AuthContainerBorder(
	content: @Composable () -> Unit,
	paddingValues: PaddingValues = PaddingValues(0.dp)
) {
	Surface(
		modifier = Modifier
			.fillMaxSize()
			.padding(paddingValues),
		color = Colors.BLACK_1
	) {
		Box(
			modifier = Modifier
				.fillMaxWidth()
				.wrapContentSize(Alignment.Center)
				.padding(horizontal = 20.dp)
		) {
			content()
		}
	}
}