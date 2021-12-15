package com.example.moneyco.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun DividerLogin() {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center,
        modifier = Modifier.padding(18.dp)
    ) {
        Divider(
            color = MaterialTheme.colors.secondaryVariant,
            thickness = 0.8.dp,
            modifier = Modifier.weight(1f)
        )

        Text(text = " Ou ")

        Divider(
            color = MaterialTheme.colors.secondaryVariant,
            thickness = 0.8.dp,
            modifier = Modifier.weight(1f)
        )
    }
}