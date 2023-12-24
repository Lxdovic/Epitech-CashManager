package com.example.moulamanagerclient.ui.scan

import android.util.Log
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.hapticfeedback.HapticFeedbackType
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalHapticFeedback
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import kotlinx.coroutines.launch

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun ScanComponent() {
	val scanViewModel = hiltViewModel<ScanViewModel>()

	Text("Scan")

	val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

	if (cameraPermissionState.status.isGranted) {
		Scan(scanViewModel)
	} else if (cameraPermissionState.status.shouldShowRationale) {
		Text("Camera Permission permanently denied")
	} else {
		SideEffect { cameraPermissionState.run { launchPermissionRequest() } }
		Text("No Camera Permission")
	}

}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun Scan(scanViewModel: ScanViewModel) {
	val coroutineScope = rememberCoroutineScope()
	val modalBottomSheetState = rememberModalBottomSheetState()
	val haptic = LocalHapticFeedback.current
	val localContext = LocalContext.current
	val lifecycleOwner = LocalLifecycleOwner.current
	val cameraProviderFuture = remember {
		ProcessCameraProvider.getInstance(localContext)
	}

	val currentEan = scanViewModel.ean.collectAsState()
	val currentAmount = scanViewModel.amount.collectAsState()
	val productResult = scanViewModel.productResult.collectAsState()

	if (currentEan.value.isNotEmpty()) {
		haptic.performHapticFeedback(HapticFeedbackType.LongPress)

		scanViewModel.getProduct(currentEan.value)
	}

	AndroidView(
		modifier = Modifier.fillMaxSize(),
		factory = { context ->
			val previewView = PreviewView(context)
			val preview = Preview.Builder().build()
			val selector = CameraSelector.Builder()
				.requireLensFacing(CameraSelector.LENS_FACING_BACK)
				.build()

			preview.setSurfaceProvider(previewView.surfaceProvider)

			val imageAnalysis = ImageAnalysis.Builder().build()

			imageAnalysis.setAnalyzer(
				ContextCompat.getMainExecutor(context),
				BarcodeAnalyzer(context, scanViewModel)
			)

			runCatching {
				cameraProviderFuture.get().bindToLifecycle(
					lifecycleOwner,
					selector,
					preview,
					imageAnalysis
				)
			}.onFailure {
				Log.e("CAMERA", "Camera bind error ${it.localizedMessage}", it)
			}
			previewView
		}
	)

	if (currentEan.value.isNotEmpty()) {
		ModalBottomSheet(
			modifier = Modifier.fillMaxHeight(),
			onDismissRequest = { scanViewModel.reset() },
			sheetState = modalBottomSheetState,
			dragHandle = { BottomSheetDefaults.DragHandle() },
		) {
			Text(
				modifier = Modifier
					.padding(start = 16.dp, end = 16.dp, top = 32.dp, bottom = 32.dp)
					.align(Alignment.CenterHorizontally),
				text = "Add to cart",
				fontSize = 30.sp
			)

			Text(
				modifier = Modifier.padding(start = 16.dp, end = 16.dp),
				text = "Product characteristics",
				fontSize = 22.sp
			)

			HorizontalDivider(
				modifier = Modifier.padding(16.dp),
				thickness = 1.dp
			)

			Text(
				modifier = Modifier.padding(start = 16.dp, end = 16.dp),
				text = "Name: ${productResult.value?.name ?: ""}",
				fontSize = 16.sp
			)

			Text(
				modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 16.dp),
				text = "Name: ${productResult.value?.price ?: ""}",
				fontSize = 16.sp
			)

			Text(
				modifier = Modifier.padding(start = 16.dp, end = 16.dp, bottom = 4.dp),
				text = "Product description",
				fontSize = 22.sp
			)

			HorizontalDivider(
				modifier = Modifier.padding(16.dp),
				thickness = 1.dp
			)

			Text(
				modifier = Modifier.padding(16.dp),
				text = "Name: ${productResult.value?.description ?: ""}",
				fontSize = 16.sp,
				lineHeight = 30.sp,
			)

			HorizontalDivider(
				thickness = 1.dp
			)

			Row(
				modifier = Modifier.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {
				OutlinedTextField(
					value = currentAmount.value,
					onValueChange = { scanViewModel.setAmount(it) },
					keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
					modifier = Modifier.width(50.dp),
					singleLine = true,
				)

				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = "x ${productResult.value?.name ?: ""}",
					fontSize = 16.sp
				)

				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = "${productResult.value?.price ?: ""}",
					fontSize = 16.sp
				)
			}

			HorizontalDivider(
				thickness = 1.dp
			)
			Row(
				modifier = Modifier.padding(16.dp),
				horizontalArrangement = Arrangement.SpaceBetween,
				verticalAlignment = Alignment.CenterVertically
			) {

				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = "Total",
					fontSize = 16.sp
				)

				Text(
					modifier = Modifier.padding(start = 16.dp),
					text = "$${productResult.value?.price?.times(currentAmount.value.toIntOrNull() ?: 0) ?: 0}",
					fontSize = 16.sp
				)
			}

			Row(
				modifier = Modifier
					.padding(start = 16.dp, end = 16.dp, top = 16.dp, bottom = 48.dp)
					.fillMaxWidth()
					.fillMaxHeight(),
				horizontalArrangement = Arrangement.End,
				verticalAlignment = Alignment.Bottom
			) {
				OutlinedButton(
					onClick = {
						coroutineScope.launch {
							modalBottomSheetState.hide()
							scanViewModel.reset()
						}
					},
					modifier = Modifier.width(100.dp),
					shape = RoundedCornerShape(8.dp)
				) {
					Text(
						text = "Cancel",
						fontSize = 16.sp
					)
				}

				Spacer(modifier = Modifier.width(16.dp))

				Button(
					onClick = {
						coroutineScope.launch {
							modalBottomSheetState.hide()
							scanViewModel.reset()
						}
					},
					modifier = Modifier.width(100.dp),
					shape = RoundedCornerShape(8.dp)
				) {
					Text(
						text = "Add",
						fontSize = 16.sp
					)
				}
			}
		}
	}

}