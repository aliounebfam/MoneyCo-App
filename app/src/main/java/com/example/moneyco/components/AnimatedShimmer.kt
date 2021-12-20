package com.example.moneyco.components


import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp


@Composable
fun animatedShimmer(): Brush {
    val shimmerColors = listOf(
        Color.LightGray.copy(alpha = 0.7f),
        Color.LightGray.copy(alpha = 0.3f),
        Color.LightGray.copy(alpha = 0.7f),
    )

    val transition = rememberInfiniteTransition()
    val translateAnim = transition.animateFloat(
        initialValue = 0f,
        targetValue = 1000f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 300,
                easing = FastOutSlowInEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    return Brush.linearGradient(
        colors = shimmerColors,
        start = Offset.Zero,
        end = Offset(x = translateAnim.value, y = translateAnim.value)
    )

}

@Composable
fun ShimmerProfil(brush: Brush = animatedShimmer()) {

    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Spacer(
            modifier = Modifier
                .size(110.dp)
                .clip(CircleShape)
                .background(brush)
        )
        Spacer(modifier = Modifier.height(6.dp))
        Spacer(
            modifier = Modifier
                .height(27.dp)
                .fillMaxWidth(0.5f)
                .clip(RoundedCornerShape(10.dp))
                .background(brush)
        )

    }

}

@Composable
fun ShimmerInfoProfil(brush: Brush = animatedShimmer()) {
    Column {
        for (i in 1..4) {
            if (i == 1) Spacer(modifier = Modifier.height(3.dp))
            Spacer(
                modifier = Modifier
                    .height(59.dp)
                    .fillMaxWidth(0.9f)
                    .clip(RoundedCornerShape(10.dp))
                    .background(brush)
            )
            if (i != 4) {
                Spacer(modifier = Modifier.height(33.dp))
            }
        }
    }
}