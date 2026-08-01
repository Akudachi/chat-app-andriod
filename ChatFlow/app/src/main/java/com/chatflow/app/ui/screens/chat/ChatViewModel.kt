package com.chatflow.app.ui.screens.chat

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chatflow.app.domain.model.Message
import com.chatflow.app.domain.model.MessageStatus
import com.chatflow.app.domain.model.MessageType
import com.chatflow.app.domain.model.User
import com.chatflow.app.domain.repository.AuthRepository
import com.chatflow.app.domain.repository.ChatRepository
import com.chatflow.app.domain.repository.MessageRepository
import com.chatflow.app.domain.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChatViewModel @Inject constructor(
    private val authRepository: AuthRepository,
    private val chatRepository: ChatRepository,
    private val messageRepository: MessageRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private val _messages = MutableStateFlow<List<Message>>(emptyList())
    val messages: StateFlow<List<Message>> = _messages.asStateFlow()

    private val _currentUser = MutableStateFlow<User?>(null)
    val currentUser: StateFlow<User?> = _currentUser.asStateFlow()

    private val _otherUser = MutableStateFlow<User?>(null)
    val otherUser: StateFlow<User?> = _otherUser.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _isTyping = MutableStateFlow(false)
    val isTyping: StateFlow<Boolean> = _isTyping.asStateFlow()

    fun loadChatData(chatId: String) {
        viewModelScope.launch {
            _isLoading.value = true

            val userResult = userRepository.getCurrentUser()
            if (userResult.isSuccess && userResult.getOrNull() != null) {
                _currentUser.value = userResult.getOrNull()!!
            }

            messageRepository.getMessagesFlow(chatId).collect { messageList ->
                _messages.value = messageList
                _isLoading.value = false
            }
        }
    }

    fun sendMessage(chatId: String, text: String) {
        viewModelScope.launch {
            val currentUser = _currentUser.value ?: return@launch
            val message = Message(
                messageId = "",
                senderId = currentUser.userId,
                text = text,
                type = MessageType.TEXT,
                mediaUrl = null,
                mediaType = null,
                mediaDuration = null,
                replyToMessageId = null,
                replyToText = null,
                replyToSenderId = null,
                timestamp = System.currentTimeMillis(),
                status = MessageStatus.SENDING,
                isEdited = false,
                editedAt = null,
                isDeleted = false
            )

            val result = messageRepository.sendMessage(chatId, message)
            if (result.isSuccess) {
                chatRepository.updateLastMessage(
                    chatId,
                    text,
                    currentUser.userId,
                    System.currentTimeMillis()
                )
            } else {
                _errorMessage.value = result.exceptionOrNull()?.message ?: "Failed to send message"
            }
        }
    }

    fun markAsRead(chatId: String) {
        viewModelScope.launch {
            val currentUser = _currentUser.value ?: return@launch
            messageRepository.markMessageAsRead(chatId, "", currentUser.userId)
            chatRepository.markAsRead(chatId, currentUser.userId)
        }
    }

    fun updateTypingStatus(chatId: String, isTyping: Boolean) {
        viewModelScope.launch {
            val currentUser = _currentUser.value ?: return@launch
            chatRepository.updateTypingStatus(chatId, currentUser.userId, isTyping)
        }
    }

    fun clearError() {
        _errorMessage.value = null
    }
}
