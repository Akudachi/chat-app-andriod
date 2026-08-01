package com.chatflow.app.ui.screens.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()

    init {
        checkAuthState()
    }

    private fun checkAuthState() {
        viewModelScope.launch {
            delay(2000) // Show splash for 2 seconds

            val userResult = userRepository.getCurrentUser()
            if (userResult.isSuccess && userResult.getOrNull() != null) {
                val currentUser = userResult.getOrNull()!!
                if (currentUser.name.isEmpty()) {
                    _navigationEvent.value = NavigationEvent.NavigateToOnboarding
                } else {
                    _navigationEvent.value = NavigationEvent.NavigateToHome
                }
            } else {
                _navigationEvent.value = NavigationEvent.NavigateToLogin
            }
        }
    }

    fun onNavigationHandled() {
        _navigationEvent.value = null
    }
}

sealed class NavigationEvent {
    object NavigateToLogin : NavigationEvent()
    object NavigateToHome : NavigationEvent()
    object NavigateToOnboarding : NavigationEvent()
}
