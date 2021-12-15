package com.example.moneyco.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.sp

@Composable
fun TextAlreadyAccount(
    onClick: () -> Unit,
    text1: String = "Vous avez déjà un compte ? ",
    text2: String = "CONNECTEZ VOUS",
    fontSize: TextUnit = 12.5.sp
) {
    Row() {
        Text(
            text = text1,
            style = TextStyle(fontSize = fontSize),
            color = MaterialTheme.colors.secondaryVariant
        )
        Text(
            text = text2,
            color = MaterialTheme.colors.primaryVariant,
            style = TextStyle(fontSize = fontSize, fontWeight = FontWeight.Bold),
            modifier = Modifier.clickable {
                onClick()
            }
        )
    }
}
