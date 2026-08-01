package com.chatflow.app.domain.repository

import kotlinx.coroutines.flow.Flow

interface StorageRepository {
    suspend fun uploadProfilePhoto(
        userId: String,
        uri: String
    ): Flow<Float>

    suspend fun uploadChatImage(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float>

    suspend fun uploadChatVideo(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float>

    suspend fun uploadChatAudio(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float>

    suspend fun uploadChatDocument(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float>

    suspend fun getDownloadUrl(path: String): Result<String>
    suspend fun deleteFile(path: String): Result<Unit>
}
