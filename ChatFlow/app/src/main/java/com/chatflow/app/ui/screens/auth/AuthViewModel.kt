package com.chatflow.app.ui.screens.auth

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.util.ValidationUtils
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AuthViewModel @Inject constructor(
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess.asStateFlow()

    private val _registerSuccess = MutableStateFlow(false)
    val registerSuccess: StateFlow<Boolean> = _registerSuccess.asStateFlow()

    private val _resetSuccess = MutableStateFlow(false)
    val resetSuccess: StateFlow<Boolean> = _resetSuccess.asStateFlow()

    fun login(email: String, password: String) {
        val emailError = ValidationUtils.getEmailErrorMessage(email)
        val passwordError = ValidationUtils.getPasswordErrorMessage(password)

        if (emailError != null || passwordError != null) {
            _errorMessage.value = emailError ?: passwordError
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.login(email, password)
            if (result.isSuccess) {
                _loginSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Login failed"
            }

            _isLoading.value = false
        }
    }

    fun register(email: String, password: String, name: String, confirmPassword: String) {
        val emailError = ValidationUtils.getEmailErrorMessage(email)
        val passwordError = ValidationUtils.getPasswordErrorMessage(password)
        val nameError = ValidationUtils.getNameErrorMessage(name)

        if (emailError != null || passwordError != null || nameError != null) {
            _errorMessage.value = emailError ?: passwordError ?: nameError
            return
        }

        if (password != confirmPassword) {
            _errorMessage.value = "Passwords do not match"
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.register(email, password, name)
            if (result.isSuccess) {
                _registerSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Registration failed"
            }

            _isLoading.value = false
        }
    }

    fun sendPasswordResetEmail(email: String) {
        val emailError = ValidationUtils.getEmailErrorMessage(email)

        if (emailError != null) {
            _errorMessage.value = emailError
            return
        }

        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val result = authRepository.sendPasswordResetEmail(email)
            if (result.isSuccess) {
                _resetSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to send reset email"
            }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearSuccess() {
        _loginSuccess.value = false
        _registerSuccess.value = false
        _resetSuccess.value = false
    }
}
