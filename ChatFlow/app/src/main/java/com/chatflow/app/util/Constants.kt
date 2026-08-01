package com.chatflow.app.util

object Constants {
    // Firestore Collections
    const val COLLECTION_USERS = "users"
    const val COLLECTION_CHATS = "chats"
    const val COLLECTION_MESSAGES = "messages"

    // Storage Paths
    const val STORAGE_PROFILE_PHOTOS = "profile_photos"
    const val STORAGE_CHAT_IMAGES = "chats/%s/images"
    const val STORAGE_CHAT_VIDEOS = "chats/%s/videos"
    const val STORAGE_CHAT_AUDIO = "chats/%s/audio"
    const val STORAGE_CHAT_DOCUMENTS = "chats/%s/documents"

    // User Fields
    const val FIELD_USER_ID = "userId"
    const val FIELD_NAME = "name"
    const val FIELD_EMAIL = "email"
    const val FIELD_PHOTO_URL = "photoUrl"
    const val FIELD_USER_STATUS = "status"
    const val FIELD_PHONE_NUMBER = "phoneNumber"
    const val FIELD_IS_ONLINE = "isOnline"
    const val FIELD_LAST_SEEN = "lastSeen"
    const val FIELD_FCM_TOKEN = "fcmToken"
    const val FIELD_CREATED_AT = "createdAt"
    const val FIELD_UPDATED_AT = "updatedAt"

    // Chat Fields
    const val FIELD_CHAT_ID = "chatId"
    const val FIELD_PARTICIPANTS = "participants"
    const val FIELD_LAST_MESSAGE = "lastMessage"
    const val FIELD_LAST_MESSAGE_TIMESTAMP = "lastMessageTimestamp"
    const val FIELD_LAST_MESSAGE_SENDER_ID = "lastMessageSenderId"
    const val FIELD_UNREAD_COUNT = "unreadCount"
    const val FIELD_IS_TYPING = "isTyping"

    // Message Fields
    const val FIELD_MESSAGE_ID = "messageId"
    const val FIELD_SENDER_ID = "senderId"
    const val FIELD_TEXT = "text"
    const val FIELD_TYPE = "type"
    const val FIELD_MEDIA_URL = "mediaUrl"
    const val FIELD_MEDIA_TYPE = "mediaType"
    const val FIELD_MEDIA_DURATION = "mediaDuration"
    const val FIELD_REPLY_TO_MESSAGE_ID = "replyToMessageId"
    const val FIELD_REPLY_TO_TEXT = "replyToText"
    const val FIELD_REPLY_TO_SENDER_ID = "replyToSenderId"
    const val FIELD_TIMESTAMP = "timestamp"
    const val FIELD_STATUS = "status"
    const val FIELD_IS_EDITED = "isEdited"
    const val FIELD_EDITED_AT = "editedAt"
    const val FIELD_IS_DELETED = "isDeleted"

    // Message Types
    const val MESSAGE_TYPE_TEXT = "TEXT"
    const val MESSAGE_TYPE_IMAGE = "IMAGE"
    const val MESSAGE_TYPE_VIDEO = "VIDEO"
    const val MESSAGE_TYPE_AUDIO = "AUDIO"
    const val MESSAGE_TYPE_DOCUMENT = "DOCUMENT"
    const val MESSAGE_TYPE_LOCATION = "LOCATION"

    // Message Status
    const val MESSAGE_STATUS_SENDING = "SENDING"
    const val MESSAGE_STATUS_SENT = "SENT"
    const val MESSAGE_STATUS_DELIVERED = "DELIVERED"
    const val MESSAGE_STATUS_READ = "READ"
    const val MESSAGE_STATUS_FAILED = "FAILED"

    // Pagination
    const val DEFAULT_PAGE_SIZE = 50
    const val MAX_IMAGE_SIZE = 10 * 1024 * 1024L // 10MB
    const val MAX_VIDEO_SIZE = 100 * 1024 * 1024L // 100MB
    const val MAX_AUDIO_SIZE = 10 * 1024 * 1024L // 10MB
    const val MAX_DOCUMENT_SIZE = 25 * 1024 * 1024L // 25MB

    // Typing Timeout
    const val TYPING_TIMEOUT = 3000L // 3 seconds

    // Animation Duration
    const val ANIMATION_DURATION_SHORT = 300
    const val ANIMATION_DURATION_MEDIUM = 500
    const val ANIMATION_DURATION_LONG = 800
}
