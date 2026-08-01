package com.chatflow.app.data.model

import com.google.gson.annotations.SerializedName

data class MessageDto(
    @SerializedName("_id")
    val messageId: String = "",
    @SerializedName("senderId")
    val senderId: String = "",
    @SerializedName("text")
    val text: String = "",
    @SerializedName("type")
    val type: String = "TEXT",
    @SerializedName("mediaUrl")
    val mediaUrl: String? = null,
    @SerializedName("mediaType")
    val mediaType: String? = null,
    @SerializedName("mediaDuration")
    val mediaDuration: Long? = null,
    @SerializedName("replyToMessageId")
    val replyToMessageId: String? = null,
    @SerializedName("replyToText")
    val replyToText: String? = null,
    @SerializedName("replyToSenderId")
    val replyToSenderId: String? = null,
    @SerializedName("createdAt")
    val timestamp: Long = System.currentTimeMillis(),
    @SerializedName("status")
    val status: String = "SENT",
    @SerializedName("isEdited")
    val isEdited: Boolean = false,
    @SerializedName("editedAt")
    val editedAt: Long? = null,
    @SerializedName("isDeleted")
    val isDeleted: Boolean = false
)
