package com.chatflow.app.ui.screens.newchat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.model.User
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.domain.repository.ChatRepository
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewChatViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val userRepository: UserRepository,
    private val chatRepository: ChatRepository
) : ViewModel() {

    private val _users = MutableStateFlow<List<User>>(emptyList())
    val users: StateFlow<List<User>> = _users.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _chatCreated = MutableStateFlow<String?>(null)
    val chatCreated: StateFlow<String?> = _chatCreated.asStateFlow()

    init {
        loadUsers()
    }

    private fun loadUsers() {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.searchUsers("")
            if (result.isSuccess) {
                _users.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to load users"
            }
            _isLoading.value = false
        }
    }

    fun searchUsers(query: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val result = userRepository.searchUsers(query)
            if (result.isSuccess) {
                _users.value = result.getOrNull() ?: emptyList()
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Search failed"
            }
            _isLoading.value = false
        }
    }

    fun createChat(participantId: String) {
        viewModelScope.launch {
            _isLoading.value = true
            val currentUserResult = userRepository.getCurrentUser()
            if (currentUserResult.isSuccess && currentUserResult.getOrNull() != null) {
                val currentUser = currentUserResult.getOrNull()!!
                val participants = mapOf(
                    currentUser.id to true,
                    participantId to true
                )
                val result = chatRepository.createChat(participants)
                if (result.isSuccess) {
                    _chatCreated.value = result.getOrNull()
                } else {
                    _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to create chat"
                }
            } else {
                _errorMessage.value = "Failed to get current user"
            }
            _isLoading.value = false
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }

    fun clearChatCreated() {
        _chatCreated.value = null
    }
}
