package com.chatflow.app.domain.repository

import com.chatflow.app.domain.model.User
import com.google.firebase.auth.AuthResult
import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    val currentUser: Flow<User?>
    val isLoggedIn: Flow<Boolean>

    suspend fun login(email: String, password: String): Result<AuthResult>
    suspend fun register(email: String, password: String, name: String): Result<AuthResult>
    suspend fun loginWithGoogle(): Result<AuthResult>
    suspend fun sendPasswordResetEmail(email: String): Result<Unit>
    suspend fun logout(): Result<Unit>
    suspend fun deleteAccount(): Result<Unit>
}
