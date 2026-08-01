package com.chatflow.app.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.chatflow.app.ui.theme.AvatarShape

@Composable
fun ProfileAvatar(
    photoUrl: String?,
    name: String,
    modifier: Modifier = Modifier,
    size: Int = 48
) {
    Box(
        modifier = modifier.size(size.dp),
        contentAlignment = Alignment.Center
    ) {
        if (photoUrl != null) {
            AsyncImage(
                model = photoUrl,
                contentDescription = name,
                modifier = Modifier
                    .size(size.dp)
                    .clip(AvatarShape),
                error = {
                    DefaultAvatar(name = name, size = size)
                }
            )
        } else {
            DefaultAvatar(name = name, size = size)
        }
    }
}

@Composable
private fun DefaultAvatar(
    name: String,
    size: Int
) {
    val initial = name.firstOrNull()?.toString()?.uppercase() ?: "?"
    val backgroundColor = when (initial) {
        "A" -> Color(0xFFE57373)
        "B" -> Color(0xFFF06292)
        "C" -> Color(0xFFBA68C8)
        "D" -> Color(0xFF9575CD)
        "E" -> Color(0xFF7986CB)
        "F" -> Color(0xFF64B5F6)
        "G" -> Color(0xFF4FC3F7)
        "H" -> Color(0xFF4DD0E1)
        "I" -> Color(0xFF4DB6AC)
        "J" -> Color(0xFF81C784)
        "K" -> Color(0xFFAED581)
        "L" -> Color(0xFFFFB74D)
        "M" -> Color(0xFFFF8A65)
        "N" -> Color(0xFFA1887F)
        "O" -> Color(0xFF90A4AE)
        else -> MaterialTheme.colorScheme.primary
    }

    Box(
        modifier = Modifier
            .size(size.dp)
            .clip(AvatarShape)
            .background(backgroundColor),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = initial,
            color = Color.White,
            style = MaterialTheme.typography.titleMedium,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.Center
        )
    }
}
