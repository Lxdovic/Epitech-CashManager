package com.example.moulamanagerclient.data.repositories.auth

import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.auth.RegisterRequest
import com.example.moulamanagerclient.data.network.ApiHelper.handleApiResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.network.ApiService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class AuthRepository(
	private val apiService: ApiService
) {
	suspend fun login(request: LoginRequest): ApiResult<LoginResponse?> {
		return withContext(Dispatchers.IO) {
			handleApiResponse(
				request = { apiService.login(request) }
			)
		}
	}

	suspend fun register(request: RegisterRequest): ApiResult<LoginResponse?> {
		return withContext(Dispatchers.IO) {
			handleApiResponse(
				request = { apiService.register(request) }
			)
		}
	}
}