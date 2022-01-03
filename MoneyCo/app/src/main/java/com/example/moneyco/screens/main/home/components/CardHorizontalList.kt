package com.example.moneyco.screens.main.home.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.example.moneyco.data.cardItem
import com.example.moneyco.ui.theme.primary_variant

@ExperimentalMaterialApi
@Composable
fun CardList(cardList: MutableList<cardItem>) {
    LazyRow(
        contentPadding = PaddingValues(
            5.dp
        ),
        modifier = Modifier
            .clip(
                RoundedCornerShape(10.dp)
            )
            .background(primary_variant.copy(alpha = 0.04f)),
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        items(items = cardList) {
            CardItem(card = it)
        }
    }
}