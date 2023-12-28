package com.example.moulamanagerclient.ui.auth.register

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moulamanagerclient.data.model.auth.LoginResponse
import com.example.moulamanagerclient.data.model.auth.RegisterRequest
import com.example.moulamanagerclient.data.network.ApiResult
import com.example.moulamanagerclient.data.repositories.auth.AuthRepository
import com.example.moulamanagerclient.utils.PreferenceManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegisterViewModel
@Inject
constructor(
	private val authRepository: AuthRepository,
	private val preferenceManager: PreferenceManager,
) : ViewModel() {

	private val _registerResult: MutableStateFlow<ApiResult<LoginResponse?>> = MutableStateFlow(ApiResult.Initial)

	private val _inputEmail: MutableStateFlow<String> = MutableStateFlow("")
	val inputEmail: MutableStateFlow<String> = _inputEmail

	private val _inputUsername: MutableStateFlow<String> = MutableStateFlow("")
	val inputUsername: MutableStateFlow<String> = _inputUsername

	private val _inputPassword: MutableStateFlow<String> = MutableStateFlow("")
	val inputPassword: MutableStateFlow<String> = _inputPassword

	private val _inputConfirmPassword: MutableStateFlow<String> = MutableStateFlow("")
	val inputConfirmPassword: MutableStateFlow<String> = _inputConfirmPassword

	private val _errorMessage: MutableStateFlow<String> = MutableStateFlow("")
	val errorMessage: MutableStateFlow<String> = _errorMessage

	private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val isLoading: MutableStateFlow<Boolean> = _isLoading

	private val _navigateToCart: MutableStateFlow<Boolean> = MutableStateFlow(false)
	val navigateToCart: MutableStateFlow<Boolean> = _navigateToCart

	fun setEmail(email: String) {
		_inputEmail.value = email
	}

	fun setUsername(username: String) {
		_inputUsername.value = username
	}

	fun setPassword(password: String) {
		_inputPassword.value = password
	}

	fun setConfirmPassword(confirmPassword: String) {
		_inputConfirmPassword.value = confirmPassword
	}

	fun performRegister() {
		if (_inputEmail.value.isBlank() || _inputUsername.value.isBlank() || _inputPassword.value.isBlank() || _inputConfirmPassword.value.isBlank()) {
			_errorMessage.value = "All fields are required"
			return
		}

		if (_inputPassword.value != _inputConfirmPassword.value) {
			_errorMessage.value = "Passwords do not match"
			return
		}


		val request = RegisterRequest(
			email = _inputEmail.value,
			username = _inputUsername.value,
			password = _inputPassword.value
		)

		viewModelScope.launch {
			_isLoading.value = true
			_errorMessage.value = ""
			_registerResult.value = authRepository.register(request)

			when (val result = _registerResult.value) {
				is ApiResult.Success -> {
					result.data?.token?.let { token ->
						preferenceManager.setValue("token", token)
						navigateToCart.value = true
					}
				}

				is ApiResult.Error -> {
					_errorMessage.value = result.errorInfo.message
				}

				ApiResult.Initial -> {
					_errorMessage.value = ""
				}
			}

			_isLoading.value = false
		}


	}

}