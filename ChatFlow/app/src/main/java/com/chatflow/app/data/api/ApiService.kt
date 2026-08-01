package com.chatflow.app.data.api

import com.chatflow.app.data.model.ChatDto
import com.chatflow.app.data.model.MessageDto
import com.chatflow.app.data.model.UserDto
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

// API Service for backend communication
interface ApiService {

    @POST("auth/register")
    suspend fun register(@Body request: RegisterRequest): AuthResponse

    @POST("auth/login")
    suspend fun login(@Body request: LoginRequest): AuthResponse

    @GET("auth/me")
    suspend fun getCurrentUser(@Header("Authorization") token: String): UserResponse

    @PUT("auth/profile")
    suspend fun updateProfile(
        @Header("Authorization") token: String,
        @Body request: UpdateProfileRequest
    ): UserResponse

    @POST("auth/logout")
    suspend fun logout(@Header("Authorization") token: String): LogoutResponse

    @POST("chats")
    suspend fun createChat(
        @Header("Authorization") token: String,
        @Body request: CreateChatRequest
    ): CreateChatResponse

    @GET("chats")
    suspend fun getChats(@Header("Authorization") token: String): ChatsResponse

    @GET("chats/{chatId}/messages")
    suspend fun getMessages(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Query("limit") limit: Int = 50
    ): MessagesResponse

    @POST("chats/{chatId}/messages")
    suspend fun sendMessage(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Body request: SendMessageRequest
    ): MessageResponse

    @PUT("chats/{chatId}/typing")
    suspend fun updateTypingStatus(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String,
        @Body request: TypingStatusRequest
    ): SuccessResponse

    @PUT("chats/{chatId}/read")
    suspend fun markAsRead(
        @Header("Authorization") token: String,
        @Path("chatId") chatId: String
    ): SuccessResponse

    @GET("chats/search/users")
    suspend fun searchUsers(
        @Header("Authorization") token: String,
        @Query("query") query: String
    ): UsersResponse

    @Multipart
    @POST("upload/image")
    suspend fun uploadImage(
        @Header("Authorization") token: String,
        @Part image: okhttp3.MultipartBody.Part
    ): UploadResponse

    @Multipart
    @POST("upload/voice")
    suspend fun uploadVoice(
        @Header("Authorization") token: String,
        @Part voice: okhttp3.MultipartBody.Part,
        @Part duration: okhttp3.MultipartBody.Part
    ): UploadResponse
}

// Request/Response DTOs
data class RegisterRequest(
    val name: String,
    val email: String,
    val password: String
)

data class LoginRequest(
    val email: String,
    val password: String
)

data class UpdateProfileRequest(
    val name: String? = null,
    val status: String? = null,
    val photoUrl: String? = null
)

data class CreateChatRequest(
    val participantId: String
)

data class SendMessageRequest(
    val text: String,
    val type: String = "TEXT",
    val mediaUrl: String? = null
)

data class TypingStatusRequest(
    val isTyping: Boolean
)

data class AuthResponse(
    val token: String,
    val user: UserDto
)

data class UserResponse(
    val user: UserDto
)

data class LogoutResponse(
    val message: String
)

data class CreateChatResponse(
    val chatId: String
)

data class ChatsResponse(
    val chats: List<ChatDto>
)

data class MessagesResponse(
    val messages: List<MessageDto>
)

data class MessageResponse(
    val message: MessageDto
)

data class SuccessResponse(
    val success: Boolean
)

data class UsersResponse(
    val users: List<UserDto>
)

data class UploadResponse(
    val imageUrl: String? = null,
    val voiceUrl: String? = null,
    val filename: String,
    val duration: Long? = null
)

// Retrofit instance
object RetrofitClient {
    private const val BASE_URL = "https://chat-app-andriod.onrender.com/api/" // Deployed backend

    private val gson: Gson = GsonBuilder()
        .setLenient()
        .create()

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        })
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    val apiService: ApiService = retrofit.create(ApiService::class.java)
}
