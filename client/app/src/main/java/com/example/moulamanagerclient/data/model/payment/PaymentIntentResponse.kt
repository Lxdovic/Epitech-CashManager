package com.example.moulamanagerclient.data.model.payment

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class PaymentIntentResponse(
	@Json(name = "paymentIntentId")
	val paymentIntentId: String,
	@Json(name = "clientSecret")
	val clientSecret: String,
	@Json(name = "ephemeralKey")
	val ephemeralKey: String,
	@Json(name = "clientId")
	val clientId: String,
)