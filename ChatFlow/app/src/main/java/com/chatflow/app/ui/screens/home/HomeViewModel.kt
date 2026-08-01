package com.chatflow.app.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.model.Chat
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
class HomeViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _chats = MutableStateFlow<List<Chat>>(emptyList())
    val chats: StateFlow<List<Chat>> = _chats.asStateFlow()

    private val _isLoading = MutableStateFlow(true)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    init {
        loadUserData()
    }

    private fun loadUserData() {
        viewModelScope.launch {
            val userResult = userRepository.getCurrentUser()
            if (userResult.isSuccess && userResult.getOrNull() != null) {
                val user = userResult.getOrNull()!!
                _currentUser.value = user
                loadChats(user.userId)
            } else {
                _isLoading.value = false
                _errorMessage.value = "Failed to load user data"
            }
        }
    }

    private fun loadChats(userId: String) {
        viewModelScope.launch {
            chatRepository.getUserChatsFlow(userId).collect { chatList ->
                _chats.value = chatList
                _isLoading.value = false
            }
        }
    }

    fun deleteChat(chatId: String) {
        viewModelScope.launch {
            val result = chatRepository.deleteChat(chatId)
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to delete chat"
            }
        }
    }

    fun archiveChat(chatId: String) {
        viewModelScope.launch {
            val userId = _currentUser.value?.userId ?: return@launch
            val result = chatRepository.archiveChat(chatId, userId)
            if (result.isFailure) {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to archive chat"
            }
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
}
