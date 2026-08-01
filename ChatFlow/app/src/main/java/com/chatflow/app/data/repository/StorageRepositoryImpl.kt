package com.chatflow.app.data.repository

import android.net.Uri
import com.chatflow.app.data.api.ApiService
import com.chatflow.app.data.api.RetrofitClient
import com.chatflow.app.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import java.io.File
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StorageRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : StorageRepository {

    private val apiService = RetrofitClient.apiService

    override suspend fun uploadProfilePhoto(
        userId: String,
        uri: String
    ): Flow<Float> {
        return flow {
            emit(0f)

            val file = File(uri)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val token = authRepository.getBearerToken()
            if (token == null) {
                throw Exception("Not authenticated")
            }

            val response = apiService.uploadImage(token, body)
            emit(1f)
        }
    }

    override suspend fun uploadChatImage(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float> {
        return flow {
            emit(0f)

            val file = File(uri)
            val requestFile = file.asRequestBody("image/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("image", file.name, requestFile)

            val token = authRepository.getBearerToken()
            if (token == null) {
                throw Exception("Not authenticated")
            }

            val response = apiService.uploadImage(token, body)
            emit(1f)
        }
    }

    override suspend fun uploadChatVideo(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float> {
        // Video not supported per requirements
        return flow { emit(1f) }
    }

    override suspend fun uploadChatAudio(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float> {
        return flow {
            emit(0f)

            val file = File(uri)
            val requestFile = file.asRequestBody("audio/*".toMediaTypeOrNull())
            val body = MultipartBody.Part.createFormData("voice", file.name, requestFile)

            val durationPart = "0".toRequestBody("text/plain".toMediaTypeOrNull())

            val token = authRepository.getBearerToken()
            if (token == null) {
                throw Exception("Not authenticated")
            }

            val response = apiService.uploadVoice(token, body, durationPart)
            emit(1f)
        }
    }

    override suspend fun uploadChatDocument(
        chatId: String,
        messageId: String,
        uri: String
    ): Flow<Float> {
        // Documents not supported per requirements
        return flow { emit(1f) }
    }

    override suspend fun getDownloadUrl(path: String): Result<String> {
        // In MongoDB version, URLs are returned directly from upload
        return Result.success(path)
    }

    override suspend fun deleteFile(path: String): Result<Unit> {
        // File deletion would require additional backend endpoint
        return Result.success(Unit)
    }
}
