package com.chatflow.app.data.repository

import com.chatflow.app.data.api.ApiService
import com.chatflow.app.data.api.RetrofitClient
import com.chatflow.app.data.api.UpdateProfileRequest
import com.chatflow.app.data.model.UserDto
import com.chatflow.app.domain.model.User
import com.chatflow.app.domain.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepositoryImpl @Inject constructor(
    private val authRepository: AuthRepositoryImpl
) : UserRepository {

    private val apiService = RetrofitClient.apiService

    override suspend fun getUser(userId: String): Result<User?> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val response = apiService.getCurrentUser(token)
            Result.success(response.user.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun getCurrentUser(): Result<User?> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val response = apiService.getCurrentUser(token)
            Result.success(response.user.toDomain())
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateUser(user: User): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.updateProfile(
                token,
                UpdateProfileRequest(user.name, user.status, user.photoUrl)
            )
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateProfilePhoto(userId: String, photoUrl: String): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.updateProfile(token, UpdateProfileRequest(photoUrl = photoUrl))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateStatus(userId: String, status: String): Result<Unit> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            apiService.updateProfile(token, UpdateProfileRequest(status = status))
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun updateOnlineStatus(userId: String, isOnline: Boolean): Result<Unit> {
        // Online status managed by backend on login/logout
        return Result.success(Unit)
    }

    override suspend fun updateLastSeen(userId: String, timestamp: Long): Result<Unit> {
        // Last seen managed by backend
        return Result.success(Unit)
    }

    override suspend fun updateFcmToken(userId: String, token: String): Result<Unit> {
        // FCM not implemented for MongoDB version
        return Result.success(Unit)
    }

    override suspend fun searchUsers(query: String): Result<List<User>> {
        return try {
            val token = authRepository.getBearerToken()
            if (token == null) return Result.failure(Exception("Not authenticated"))

            val response = apiService.searchUsers(token, query)
            Result.success(response.users.map { it.toDomain() })
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override fun getUserFlow(userId: String): Flow<User?> {
        // For real-time updates, we'd use polling or WebSocket
        return MutableStateFlow<User?>(null).asStateFlow()
    }
}

private fun UserDto.toDomain(): User {
    return User(
        id = id,
        name = name,
        email = email,
        photoUrl = photoUrl,
        status = status,
        phoneNumber = phoneNumber,
        isOnline = isOnline,
        lastSeen = lastSeen,
        createdAt = createdAt,
        updatedAt = updatedAt
    )
}
