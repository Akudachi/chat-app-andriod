package com.chatflow.app.domain.repository

import com.chatflow.app.domain.model.Message
import kotlinx.coroutines.flow.Flow

interface MessageRepository {
    suspend fun sendMessage(
        chatId: String,
        message: Message
    ): Result<String>

    suspend fun editMessage(
        chatId: String,
        messageId: String,
        newText: String
    ): Result<Unit>

    suspend fun deleteMessage(
        chatId: String,
        messageId: String
    ): Result<Unit>

    suspend fun markMessageAsRead(
        chatId: String,
        messageId: String,
        userId: String
    ): Result<Unit>

    suspend fun getMessages(chatId: String, limit: Int = 50): Result<List<Message>>
    suspend fun getMessagesBefore(chatId: String, timestamp: Long, limit: Int = 50): Result<List<Message>>

    fun getMessagesFlow(chatId: String): Flow<List<Message>>
    fun getLastMessageFlow(chatId: String): Flow<Message?>
}
