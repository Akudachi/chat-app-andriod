package com.chatflow.app.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.chatflow.app.data.api.ApiService
import com.chatflow.app.data.api.LoginRequest
import com.chatflow.app.data.api.RegisterRequest
import com.chatflow.app.data.api.RetrofitClient
import com.chatflow.app.domain.model.User
import com.chatflow.app.domain.repository.AuthRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject
import javax.inject.Singleton

// Extension for DataStore
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "auth")

@Singleton
class AuthRepositoryImpl @Inject constructor(
    private val context: Context
) : AuthRepository {

    private val apiService = RetrofitClient.apiService
    private val TOKEN_KEY = stringPreferencesKey("jwt_token")

    override val currentUser: Flow<User?> = runBlocking {
        // Load user from local storage or API
        MutableStateFlow<User?>(null).asStateFlow()
    }

    override val isLoggedIn: Flow<Boolean> = runBlocking {
        val token = context.dataStore.data.first()[TOKEN_KEY]
        MutableStateFlow(token != null).asStateFlow()
    }

    override suspend fun login(email: String, password: String): Result<Unit> {
        return try {
            val response = apiService.login(LoginRequest(email, password))
            saveToken(response.token)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun register(email: String, password: String, name: String): Result<Unit> {
        return try {
            val response = apiService.register(RegisterRequest(name, email, password))
            saveToken(response.token)
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun loginWithGoogle(): Result<Unit> {
        return Result.failure(NotImplementedError("Google Sign-In not implemented with MongoDB"))
    }

    override suspend fun sendPasswordResetEmail(email: String): Result<Unit> {
        return Result.failure(NotImplementedError("Password reset not implemented"))
    }

    override suspend fun logout(): Result<Unit> {
        return try {
            val token = getToken()
            if (token != null) {
                apiService.logout("Bearer $token")
            }
            clearToken()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }

    override suspend fun deleteAccount(): Result<Unit> {
        return Result.failure(NotImplementedError("Account deletion not implemented"))
    }

    private suspend fun saveToken(token: String) {
        context.dataStore.edit { preferences ->
            preferences[TOKEN_KEY] = token
        }
    }

    private suspend fun getToken(): String? {
        return context.dataStore.data.first()[TOKEN_KEY]
    }

    private suspend fun clearToken() {
        context.dataStore.edit { preferences ->
            preferences.remove(TOKEN_KEY)
        }
    }

    fun getBearerToken(): String? {
        return runBlocking { getToken() }?.let { "Bearer $it" }
    }
}
