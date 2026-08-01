package com.chatflow.app.domain.model

enum class MessageType {
    TEXT,
    IMAGE,
    VIDEO,
    AUDIO,
    DOCUMENT,
    LOCATION
}

enum class MessageStatus {
    SENDING,
    SENT,
    DELIVERED,
    READ,
    FAILED
}

data class Message(
    val messageId: String,
    val senderId: String,
    val text: String,
    val type: MessageType,
    val mediaUrl: String?,
    val mediaType: String?,
    val mediaDuration: Long?,
    val replyToMessageId: String?,
    val replyToText: String?,
    val replyToSenderId: String?,
    val timestamp: Long,
    val status: MessageStatus,
    val isEdited: Boolean,
    val editedAt: Long?,
    val isDeleted: Boolean
)
