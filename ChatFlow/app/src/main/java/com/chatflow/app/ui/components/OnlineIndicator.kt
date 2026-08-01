package com.chatflow.app.ui.components

import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.chatflow.app.ui.theme.OnlineStatus

@Composable
fun OnlineIndicator(
    modifier: Modifier = Modifier,
    isOnline: Boolean = true
) {
    if (isOnline) {
        val infiniteTransition = rememberInfiniteTransition(label = "pulse")
        val scale = infiniteTransition.animateFloat(
            initialValue = 1f,
            targetValue = 1.5f,
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1000),
                repeatMode = RepeatMode.Restart
            ),
            label = "pulse_scale"
        )

        Box(
            modifier = modifier.size(12.dp),
            contentAlignment = Alignment.Center
        ) {
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .clip(CircleShape)
                    .background(OnlineStatus)
            )
            Box(
                modifier = Modifier
                    .size(12.dp)
                    .scale(scale.value)
                    .clip(CircleShape)
                    .background(OnlineStatus.copy(alpha = 0.3f))
            )
        }
    }
}
