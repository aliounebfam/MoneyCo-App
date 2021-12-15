package com.example.moneyco.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.example.moneyco.R

@ExperimentalMaterialApi
@Composable
fun SignInButtonUi(
    modifier: Modifier = Modifier,
    text: String = "S'inscrire avec Google",
    icon: Painter = painterResource(id = R.drawable.ic_google_icon),
    description: String = "Google icon",
    onClicked: () -> Unit
) {

    androidx.compose.material.Surface(
        onClick = { onClicked() },
        shape = RoundedCornerShape(9.dp),
        border = BorderStroke(width = 1.dp, color = Color.LightGray)
    ) {
        Row(
            modifier = modifier
                .padding(
                    start = 12.dp,
                    end = 16.dp,
                    top = 12.dp,
                    bottom = 12.dp
                )
        ) {
            Icon(
                painter = icon,
                contentDescription = description,
                tint = Color.Unspecified
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = text, style = TextStyle(Color(0xFF1D2424)))

        }
    }
}
