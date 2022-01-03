package com.example.moneyco.screens.main.revenu.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.moneyco.components.animatedShimmer

@Composable
fun LoadingAddTransactionItem(
    brush: Brush = animatedShimmer(),
    padding: Dp = 15.dp,
    boucle: Int = 15,
    height: Dp = 55.dp,
    rounded: Dp = 15.dp,
    spacer: Dp = 15.dp
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(padding)
    ) {
        for (i in 1..boucle) {
            Spacer(
                modifier = Modifier
                    .height(height)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(rounded))
                    .background(brush)
            )
            Spacer(modifier = Modifier.height(spacer))
        }
    }
}
