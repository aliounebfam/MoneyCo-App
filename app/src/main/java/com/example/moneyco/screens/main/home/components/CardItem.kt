package com.example.moneyco.screens.main.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.data.cardItem
import com.example.moneyco.ui.theme.Merienda
import com.example.moneyco.ui.theme.Nunito

@ExperimentalMaterialApi
@Composable
fun CardItem(
    card: cardItem
) {
    Card(
        modifier = Modifier
            .padding(5.dp),
        onClick = {},
        shape = RoundedCornerShape(8.dp),
        backgroundColor = card.color,
        elevation = 6.dp,

        ) {
        Column(
            modifier = Modifier.padding(15.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            Text(
                text = card.title,
                fontSize = MaterialTheme.typography.h6.fontSize,
                fontFamily = Merienda
            )
            Text(
                text = "Montant : ${card.montant}",
                fontSize = 17.sp,
                fontFamily = Nunito
            )
            Text(
                text = card.subtitle,
                fontSize = 14.sp,
                modifier = Modifier.alpha(ContentAlpha.medium)
            )

        }
    }
}
