package com.chatflow.app.data.repository

import com.chatflow.app.data.api.ApiService
import com.chatflow.app.data.api.RetrofitClient
import com.chatflow.app.data.api.SendMessageRequest
import com.chatflow.app.data.model.MessageDto
import com.chatflow.app.domain.model.Message
import com.chatflow.app.domain.model.MessageStatus
import com.chatflow.app.domain.model.MessageType
import com.chatflow.app.domain.repository.MessageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class MessageRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : MessageRepository {

    private val apiService = RetrofitClient.apiService

    override suspend fun sendMessage(chatId: String, message: Message): Result<String> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val request = SendMessageRequest(
                text = message.text,
                type = message.type.name,
                mediaUrl = message.mediaUrl
            )

            val response = apiService.sendMessage(token, chatId, request)
            Result.success(response.message.messageId)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun editMessage(
        chatId: String,
        messageId: String,
        newText: String
    ): Result<Unit> {
        return Result.failure(NotImplementedError("Edit message not implemented"))
    }

    override suspend fun deleteMessage(chatId: String, messageId: String): Result<Unit> {
        return Result.failure(NotImplementedError("Delete message not implemented"))
    }

    override suspend fun markMessageAsRead(
        chatId: String,
        messageId: String,
        userId: String
    ): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.markAsRead(token, chatId)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMessages(chatId: String, limit: Int): Result<List<Message>> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val response = apiService.getMessages(token, chatId, limit)
            Result.success(response.messages.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getMessagesBefore(
        chatId: String,
        timestamp: Long,
        limit: Int
    ): Result<List<Message>> {
        // This would require additional API endpoint
        return getMessages(chatId, limit)
    }

    override fun getMessagesFlow(chatId: String): Flow<List<Message>> {
        // For real-time updates, we'd use WebSocket events
        return MutableStateFlow<List<Message>>(emptyList()).asStateFlow()
    }

    override fun getLastMessageFlow(chatId: String): Flow<Message?> {
        // For real-time updates, we'd use WebSocket events
        return MutableStateFlow<Message?>(null).asStateFlow()
    }
}

private fun MessageDto.toDomain(): Message {
    return Message(
        messageId = messageId,
        senderId = senderId,
        text = text,
        type = when (type.uppercase()) {
            "TEXT" -> MessageType.TEXT
            "IMAGE" -> MessageType.IMAGE
            "AUDIO" -> MessageType.AUDIO
            else -> MessageType.TEXT
        },
        mediaUrl = mediaUrl,
        mediaType = mediaType,
        mediaDuration = mediaDuration,
        replyToMessageId = replyToMessageId,
        replyToText = replyToText,
        replyToSenderId = replyToSenderId,
        timestamp = timestamp,
        status = when (status.uppercase()) {
            "SENDING" -> MessageStatus.SENDING
            "SENT" -> MessageStatus.SENT
            "DELIVERED" -> MessageStatus.DELIVERED
            "READ" -> MessageStatus.READ
            "FAILED" -> MessageStatus.FAILED
            else -> MessageStatus.SENT
        },
        isEdited = isEdited,
        editedAt = editedAt,
        isDeleted = isDeleted
    )
}
