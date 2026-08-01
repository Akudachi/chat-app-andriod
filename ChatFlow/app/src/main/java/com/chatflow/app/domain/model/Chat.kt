package com.chatflow.app.domain.model

data class Chat(
    val chatId: String,
    val participants: Map<String, Boolean>,
    val lastMessage: String,
    val lastMessageTimestamp: Long,
    val lastMessageSenderId: String,
    val unreadCount: Map<String, Int>,
    val isTyping: Map<String, Boolean>,
    val createdAt: Long,
    val updatedAt: Long
)
