package com.chatflow.app.data.model

import com.google.gson.annotations.SerializedName

data class ChatDto(
    @SerializedName("chatId")
    val chatId: String = "",
    @SerializedName("participants")
    val participants: List<ParticipantDto> = emptyList(),
    @SerializedName("lastMessage")
    val lastMessage: String = "",
    @SerializedName("lastMessageTimestamp")
    val lastMessageTimestamp: Long = 0L,
    @SerializedName("lastMessageSenderId")
    val lastMessageSenderId: String = "",
    @SerializedName("unreadCount")
    val unreadCount: Map<String, Int> = emptyMap(),
    @SerializedName("isTyping")
    val isTyping: Map<String, Boolean> = emptyMap(),
    @SerializedName("otherUser")
    val otherUser: UserDto? = null,
    @SerializedName("createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @SerializedName("updatedAt")
    val updatedAt: Long = System.currentTimeMillis()
)

data class ParticipantDto(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("isOnline")
    val isOnline: Boolean = false,
    @SerializedName("lastSeen")
    val lastSeen: Long = 0L
)
