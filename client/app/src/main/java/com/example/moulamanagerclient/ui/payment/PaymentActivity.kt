package com.example.moulamanagerclient.ui.payment

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import com.stripe.android.paymentsheet.PaymentSheetResult
import com.stripe.android.paymentsheet.rememberPaymentSheet


@Composable
fun PaymentActivity(
	viewModel: PaymentViewModel = hiltViewModel()
) {
	val paymentSheet = rememberPaymentSheet(::onPaymentSheetResult)
	val paymentIntentClientSecret by viewModel.paymentIntentClientSecret.collectAsState()
	val customerConfig by viewModel.customerConfig.collectAsState()

	LaunchedEffect(Unit) {
		viewModel.fetchPaymentIntentAndCustomerConfig()
	}

	PaymentButton(viewModel, paymentSheet, paymentIntentClientSecret, customerConfig)
}

private fun onPaymentSheetResult(paymentSheetResult: PaymentSheetResult) {
	when (paymentSheetResult) {
		is PaymentSheetResult.Canceled -> {
			Log.i("PaymentSheet", "Payment canceled")
		}

		is PaymentSheetResult.Failed -> {
			Log.i("PaymentSheet", "Payment failed")
		}

		is PaymentSheetResult.Completed -> {
			// Display for example, an order confirmation screen
			Log.i("PaymentSheet", "Payment completed")
		}
	}
}