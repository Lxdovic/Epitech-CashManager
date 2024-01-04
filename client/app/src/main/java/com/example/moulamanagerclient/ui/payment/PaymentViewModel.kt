package com.example.moulamanagerclient.ui.payment

import androidx.lifecycle.ViewModel
import com.example.moulamanagerclient.data.repositories.payment.PaymentRepository
import com.stripe.android.paymentsheet.PaymentSheet
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
	private val paymentRepository: PaymentRepository
) : ViewModel() {

	private val _paymentIntentClientSecret = MutableStateFlow<String?>(null)
	val paymentIntentClientSecret = _paymentIntentClientSecret.asStateFlow()

	private val _customerConfig = MutableStateFlow<PaymentSheet.CustomerConfiguration?>(null)
	val customerConfig = _customerConfig.asStateFlow()

	suspend fun fetchPaymentIntentAndCustomerConfig() {
		val response = paymentRepository.createPaymentIntent()
		if (response.isSuccessful) {
			val paymentIntentResponse = response.body()
			_paymentIntentClientSecret.value = paymentIntentResponse?.clientSecret
			_customerConfig.value = PaymentSheet.CustomerConfiguration(
				paymentIntentResponse?.clientId.orEmpty(),
				paymentIntentResponse?.ephemeralKey.orEmpty()
			)
		}
	}
}