package com.example.moneyco.components

import android.graphics.Color.rgb
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Backspace
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.ui.theme.MoneyCoTheme


@Composable
fun CustomKeyboard() {
    val inputVal = remember { mutableStateOf("") }

    CustomKeyboard(
        input = inputVal.value,
        onClick = { digit ->
            inputVal.value += digit.toString()
        },
        onClick2 = {
            inputVal.value = inputVal.value.dropLast(1)
        }
    )
}

@Composable
fun CustomKeyboard(
    modifier: Modifier = Modifier,
    input: String,
    onClick: (digit: Char) -> Unit,
    onClick2: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
    ) {

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(number = 1, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 2, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 3, onClick = onClick, modifier = Modifier.weight(1f))
        }
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(number = 4, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 5, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 6, onClick = onClick, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            NumberButton(number = 7, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 8, onClick = onClick, modifier = Modifier.weight(1f))
            NumberButton(number = 9, onClick = onClick, modifier = Modifier.weight(1f))
        }

        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Spacer(modifier = Modifier.weight(1f))
            NumberButton(number = 0, onClick = onClick, modifier = Modifier.weight(1f))
            IconButton(
                onClick = {
                    onClick2()
                },
                modifier = Modifier
                    .weight(1f)
                    .aspectRatio(1.08f)
                    .padding(3.dp)
            ) {
                Icon(
                    Icons.Rounded.Backspace,
                    contentDescription = "Backspace Icon",
                    Modifier
                        .size(45.dp),
                    tint = Color(rgb(36, 55, 137))
                )
            }
        }
    }
}

@Composable
private fun NumberButton(
    number: Int,
    onClick: (digit: Char) -> Unit,
    modifier: Modifier = Modifier
) {
    TextButton(
        onClick = {
            onClick(number.digitToChar())
        },
        modifier = modifier
            .aspectRatio(1.08f)
            .padding(4.dp)
    ) {
        Text(
            text = number.toString(),
            fontSize = 35.sp,
            color = MaterialTheme.colors.primaryVariant
        )
    }
}


@Preview
@Composable
fun CustomKEye() {
    MoneyCoTheme {
        NumberButton(number = 3, onClick = {})
    }
}

@Preview
@Composable
fun CustomKEy() {
    CustomKeyboard()
}