package com.example.moneyco.components

import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.ui.theme.Nunito

@Composable
fun TopAppBarSimple(text: String) {
    TopAppBar(
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Nunito,
                fontSize = 22.sp
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 10.dp
    )
}


@Composable
fun TopAppBarWithBack(text: String, onBackClick: () -> Unit = {}) {
    TopAppBar(
        title = {
            Text(
                text = text,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                fontWeight = FontWeight.SemiBold,
                fontFamily = Nunito,
                fontSize = 22.sp,
            )
        },
        backgroundColor = MaterialTheme.colors.primaryVariant,
        elevation = 10.dp,
        navigationIcon = {
            IconButton(onClick = { onBackClick() }) {
                Icon(
                    imageVector = Icons.Rounded.ArrowBack,
                    contentDescription = "flèche de retour"
                )
            }
        }
    )
}
