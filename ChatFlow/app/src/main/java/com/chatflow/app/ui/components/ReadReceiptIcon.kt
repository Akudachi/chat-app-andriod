package com.chatflow.app.ui.components

import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.DoneAll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chatflow.app.domain.model.MessageStatus

@Composable
fun ReadReceiptIcon(
    status: MessageStatus,
    modifier: Modifier = Modifier
) {
    val (icon, tint) = when (status) {
        MessageStatus.SENDING -> Icons.Default.Check to MaterialTheme.colorScheme.onSurface.copy(alpha = 0.3f)
        MessageStatus.SENT -> Icons.Default.Check to MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        MessageStatus.DELIVERED -> Icons.Default.DoneAll to MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
        MessageStatus.READ -> Icons.Default.DoneAll to MaterialTheme.colorScheme.primary
        MessageStatus.FAILED -> Icons.Default.Check to Color.Red
    }

    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier.size(16.dp),
        tint = tint
    )
}
