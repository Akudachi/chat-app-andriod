package com.chatflow.app.ui.screens.onboarding

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class OnboardingViewModel @Inject constructor(
    private val userRepository: UserRepository
) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _setupSuccess = MutableStateFlow(false)
    val setupSuccess: StateFlow<Boolean> = _setupSuccess.asStateFlow()

    fun updateProfile(name: String, status: String, photoUrl: String?) {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            val userResult = userRepository.getCurrentUser()
            if (userResult.isSuccess && userResult.getOrNull() != null) {
                val user = userResult.getOrNull()!!
                val updatedUser = user.copy(
                    name = name,
                    status = status,
                    photoUrl = photoUrl
                )

                val result = userRepository.updateUser(updatedUser)
                if (result.isSuccess) {
                    _setupSuccess.value = true
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to update profile"
                }
            } else {
                _errorMessage.value = "Failed to get user data"
            }

            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearSuccess() {
        _setupSuccess.value = false
    }
}
