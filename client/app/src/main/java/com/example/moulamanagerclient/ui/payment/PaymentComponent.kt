package com.example.moulamanagerclient.ui.payment

import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.stripe.android.paymentsheet.PaymentSheet

@Composable
fun PaymentButton(
	viewModel: PaymentViewModel,
	paymentSheet: PaymentSheet,
	paymentIntentClientSecret: String?,
	customerConfig: PaymentSheet.CustomerConfiguration?
) {
	Button(
		onClick = {
			if (customerConfig != null && paymentIntentClientSecret != null) {
				presentPaymentSheet(paymentSheet, customerConfig, paymentIntentClientSecret)
			}
		}
	) {
		Text("Checkout")
	}
}

private fun presentPaymentSheet(
	paymentSheet: PaymentSheet,
	customerConfig: PaymentSheet.CustomerConfiguration,
	paymentIntentClientSecret: String
) {
	paymentSheet.presentWithPaymentIntent(
		paymentIntentClientSecret,
		PaymentSheet.Configuration(
			merchantDisplayName = "Moula Manager",
			customer = customerConfig,
			allowsDelayedPaymentMethods = true
		)
	)
}