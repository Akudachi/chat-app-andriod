package com.chatflow.app.data.repository

import com.chatflow.app.data.api.ApiService
import com.chatflow.app.data.api.RetrofitClient
import com.chatflow.app.data.api.CreateChatRequest
import com.chatflow.app.data.model.ChatDto
import com.chatflow.app.domain.model.Chat
import com.chatflow.app.domain.repository.ChatRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ChatRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : ChatRepository {

    private val apiService = RetrofitClient.apiService

    override suspend fun createChat(participants: Map<String, Boolean>): Result<String> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val participantId = participants.keys.firstOrNull { it != "current" }
                ?: return Result.failure(Exception("No participant"))

            val response = apiService.createChat(token, CreateChatRequest(participantId))
            Result.success(response.chatId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getChat(chatId: String): Result<Chat?> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val chatsResponse = apiService.getChats(token)
            val chat = chatsResponse.chats.find { it.chatId == chatId }
            Result.success(chat?.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getUserChats(userId: String): Result<List<Chat>> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val response = apiService.getChats(token)
            Result.success(response.chats.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateLastMessage(
        chatId: String,
        message: String,
        senderId: String,
        timestamp: Long
    ): Result<Unit> {
        // This is handled automatically when sending messages
        return Result.success(Unit)
    }

    override suspend fun updateTypingStatus(
        chatId: String,
        userId: String,
        isTyping: Boolean
    ): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.updateTypingStatus(token, chatId, TypingStatusRequest(isTyping))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun markAsRead(chatId: String, userId: String): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.markAsRead(token, chatId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteChat(chatId: String): Result<Unit> {
        return Result.failure(NotImplementedError("Delete chat not implemented"))
    }

    override suspend fun archiveChat(chatId: String, userId: String): Result<Unit> {
        return Result.failure(NotImplementedError("Archive chat not implemented"))
    }

    override fun getUserChatsFlow(userId: String): Flow<List<Chat>> {
        // For real-time updates, we'd use WebSocket events
        return MutableStateFlow<List<Chat>>(emptyList()).asStateFlow()
    }

    override fun getChatFlow(chatId: String): Flow<Chat?> {
        // For real-time updates, we'd use WebSocket events
        return MutableStateFlow<Chat?>(null).asStateFlow()
    }
}

private fun ChatDto.toDomain(): Chat {
    return Chat(
        chatId = chatId,
        participants = participants.associate { it.id to true },
        lastMessage = lastMessage,
        lastMessageTimestamp = lastMessageTimestamp,
        lastMessageSenderId = lastMessageSenderId,
        unreadCount = unreadCount,
        isTyping = isTyping,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
