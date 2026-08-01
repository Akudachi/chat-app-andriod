package com.chatflow.app.domain.model

data class User(
    val id: String,
    val name: String,
    val email: String,
    val photoUrl: String?,
    val status: String,
    val phoneNumber: String?,
    val isOnline: Boolean,
    val lastSeen: Long,
    val createdAt: Long,
    val updatedAt: Long
)
