package com.example.moulamanagerclient.data.repositories.payment

import com.example.moulamanagerclient.data.model.payment.PaymentIntentResponse
import com.example.moulamanagerclient.data.network.ApiService
import retrofit2.Response
import javax.inject.Inject

class PaymentRepository @Inject constructor(
	private val apiService: ApiService
) {

	suspend fun createPaymentIntent(): Response<PaymentIntentResponse> {
		return apiService.createPaymentIntent()
	}
}