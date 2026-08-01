package com.chatflow.app.ui.theme

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

val ExtraSmallShape = RoundedCornerShape(4.dp)
val SmallShape = RoundedCornerShape(8.dp)
val MediumShape = RoundedCornerShape(12.dp)
val LargeShape = RoundedCornerShape(16.dp)
val ExtraLargeShape = RoundedCornerShape(24.dp)

val ChatBubbleSentShape = RoundedCornerShape(
    topStart = 20.dp,
    topEnd = 4.dp,
    bottomStart = 20.dp,
    bottomEnd = 20.dp
)

val ChatBubbleReceivedShape = RoundedCornerShape(
    topStart = 4.dp,
    topEnd = 20.dp,
    bottomStart = 20.dp,
    bottomEnd = 20.dp
)

val AvatarShape = CircleShape
