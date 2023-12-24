package com.example.moulamanagerclient.data.network

import android.content.Context
import com.auth0.android.jwt.JWT
import com.example.moulamanagerclient.utils.PreferenceManager
import okhttp3.Interceptor
import okhttp3.Response
import java.util.*
import javax.inject.Inject

class AuthInterceptor
@Inject
constructor(
	private val preferenceManager: PreferenceManager
) : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		val originalRequest = chain.request()

		val token = preferenceManager.getValue("token")

		if (token == null || !isTokenValid(token)) {
			return chain.proceed(originalRequest)
		}

		val requestBuilder = originalRequest.newBuilder()
			.header("Authorization", "Bearer $token")
			.method(originalRequest.method, originalRequest.body)

		return chain.proceed(requestBuilder.build())
	}

	fun isLoggedIn(): Boolean {
		val token = preferenceManager.getValue("token")
		return token != null && isTokenValid(token)
	}


	private fun isTokenValid(token: String): Boolean {
		val decodedJWT = JWT(token)
		val expirationDate = decodedJWT.expiresAt
		val currentDate = Date()

		return expirationDate?.after(currentDate) ?: false
	}
}