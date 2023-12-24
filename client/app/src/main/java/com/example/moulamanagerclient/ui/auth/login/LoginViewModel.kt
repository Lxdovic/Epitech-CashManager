package com.example.moulamanagerclient.ui.auth.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moulamanagerclient.data.model.auth.LoginRequest
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.repositories.auth.AuthRepository
import com.example.moulamanagerclient.shared.ErrorStatus
import com.example.moulamanagerclient.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel
@Inject
constructor(
	private val authRepository: AuthRepository,
	private val preferenceManager: PreferenceManager,
) : ViewModel() {
	private val _loginResult: MutableStateFlow<ApiResult<LoginResponse?>> = MutableStateFlow(ApiResult.Initial)
	private val _inputPassword: MutableStateFlow<String> = MutableStateFlow("")
	private val _inputUsername: MutableStateFlow<String> = MutableStateFlow("")
	private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
	private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)

	val inputPassword: StateFlow<String> = _inputPassword
	val inputUsername: StateFlow<String> = _inputUsername
	val errorMessage: StateFlow<String> = _errorMessage
	val isLoading: StateFlow<Boolean> = _isLoading


	val navigateToMain: MutableSharedFlow<Unit> = MutableSharedFlow()


	fun setUsername(username: String) {
		_inputUsername.value = username
	}

	fun setPassword(password: String) {
		_inputPassword.value = password
	}

	fun performLogin() {

		if (_inputUsername.value.isBlank() || _inputPassword.value.isBlank()) {
			_errorMessage.value = "Username and password cannot be empty"
			return
		}

		val request = LoginRequest(
			password = _inputPassword.value,
			username = _inputUsername.value
		)

		viewModelScope.launch {
			_isLoading.value = true
			_errorMessage.value = ""
			_loginResult.value = authRepository.login(request)

			when (val result = _loginResult.value) {
				is ApiResult.Success -> {
					result.data?.token?.let { token ->
						preferenceManager.setValue("token", token)
						navigateToMain.emit(Unit)

					}
				}

				is ApiResult.Error -> {
					when (result.errorInfo.status) {
						ErrorStatus.STATUS_UNAUTHORIZED -> {
							_errorMessage.value = "Invalid credentials"
						}

						else -> {
							_errorMessage.value = "Login failed"
						}
					}
				}

				ApiResult.Initial -> {
					_errorMessage.value = ""
				}
			}

			_isLoading.value = false
		}
	}
}