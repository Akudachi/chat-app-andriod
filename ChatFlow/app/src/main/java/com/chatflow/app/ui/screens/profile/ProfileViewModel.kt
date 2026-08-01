package com.chatflow.app.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.model.User
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> = _user.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _updateSuccess = MutableStateFlow(false)
    val updateSuccess: StateFlow<Boolean> = _updateSuccess.asStateFlow()

    fun loadUser(userId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.getUser(userId)
            if (result.isSuccess) {
                _user.value = result.getOrNull()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to load user"
            }
            _isLoading.value = false
        }
    }

    fun updateProfile(name: String, status: String, photoUrl: String?) {
        viewModelScope.launch {
            val currentUser = _user.value ?: return@launch
            _isLoading.value = true
            _errorMessage.value = null

            val updatedUser = currentUser.copy(
                name = name,
                status = status,
                photoUrl = photoUrl
            )

            val result = userRepository.updateUser(updatedUser)
            if (result.isSuccess) {
                _user.value = updatedUser
                _updateSuccess.value = true
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to update profile"
            }

            _isLoading.value = false
        }
    }

    fun logout() {
        viewModelScope.launch {
            val result = authRepository.logout()
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to logout"
            }
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearSuccess() {
        _updateSuccess.value = false
    }
}
