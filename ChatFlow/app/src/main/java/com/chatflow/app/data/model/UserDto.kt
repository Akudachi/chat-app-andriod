package com.chatflow.app.data.model

import com.google.gson.annotations.SerializedName

data class UserDto(
    @SerializedName("id")
    val id: String = "",
    @SerializedName("name")
    val name: String = "",
    @SerializedName("email")
    val email: String = "",
    @SerializedName("photoUrl")
    val photoUrl: String? = null,
    @SerializedName("status")
    val status: String = "",
    @SerializedName("phoneNumber")
    val phoneNumber: String? = null,
    @SerializedName("isOnline")
    val isOnline: Boolean = false,
    @SerializedName("lastSeen")
    val lastSeen: Long = 0L,
    @SerializedName("createdAt")
    val createdAt: Long = System.currentTimeMillis(),
    @SerializedName("updatedAt")
    val updatedAt: Long = System.currentTimeMillis()
)
