package com.chatflow.app.domain.repository

import com.chatflow.app.domain.model.Chat
import kotlinx.coroutines.flow.Flow

interface ChatRepository {
    suspend fun createChat(participants: Map<String, Boolean>): Result<String>
    suspend fun getChat(chatId: String): Result<Chat?>
    suspend fun getUserChats(userId: String): Result<List<Chat>>
    suspend fun updateLastMessage(chatId: String, message: String, senderId: String, timestamp: Long): Result<Unit>
    suspend fun updateTypingStatus(chatId: String, userId: String, isTyping: Boolean): Result<Unit>
    suspend fun markAsRead(chatId: String, userId: String): Result<Unit>
    suspend fun deleteChat(chatId: String): Result<Unit>
    suspend fun archiveChat(chatId: String, userId: String): Result<Unit>
    fun getUserChatsFlow(userId: String): Flow<List<Chat>>
    fun getChatFlow(chatId: String): Flow<Chat?>
}
