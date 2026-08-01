package com.chatflow.app.domain.repository

import com.chatflow.app.domain.model.User
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUser(userId: String): Result<User?>
    suspend fun getCurrentUser(): Result<User?>
    suspend fun updateUser(user: User): Result<Unit>
    suspend fun updateProfilePhoto(userId: String, photoUrl: String): Result<Unit>
    suspend fun updateStatus(userId: String, status: String): Result<Unit>
    suspend fun updateOnlineStatus(userId: String, isOnline: Boolean): Result<Unit>
    suspend fun updateLastSeen(userId: String, timestamp: Long): Result<Unit>
    suspend fun updateFcmToken(userId: String, token: String): Result<Unit>
    suspend fun searchUsers(query: String): Result<List<User>>
    fun getUserFlow(userId: String): Flow<User?>
}
