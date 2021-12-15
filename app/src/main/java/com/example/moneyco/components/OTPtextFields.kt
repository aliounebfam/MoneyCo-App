package com.example.moneyco.components

import android.graphics.Color.rgb
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusOrder
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.ui.theme.MoneyCoTheme

@Composable
fun OTPTextFields(
    modifier: Modifier = Modifier,
    length: Int,
    inputVal: MutableState<String> = remember { mutableStateOf("") },
    onFilled: (code: String) -> Unit
) {
    var code: List<Char> by remember { mutableStateOf(listOf()) }
    val focusRequesters: List<FocusRequester> = remember {
        val temp = mutableListOf<FocusRequester>()
        repeat(length) {
            temp.add(FocusRequester())
        }
        temp
    }

    var colorInput: Color
    if (inputVal.value.length > length) {
        inputVal.value = inputVal.value.dropLast(inputVal.value.length - length)
    }



    Row(modifier = modifier) {
        (0 until length).forEach { index ->
            colorInput = if (inputVal.value.length == index) {
                Color(rgb(26, 40, 99))
            } else {
                MaterialTheme.colors.primaryVariant
            }
            OutlinedTextField(
                modifier = Modifier
                    .weight(1f)
                    .focusOrder(focusRequester = focusRequesters[index]) {
                        focusRequesters[index + 1].requestFocus()
                    },
                textStyle = MaterialTheme.typography.body1.copy(
                    textAlign = TextAlign.Center, color = Color(rgb(36, 55, 137)),
                    fontSize = 13.sp,
                    fontWeight = FontWeight.Bold
                ),
                singleLine = true,
                value = inputVal.value.getOrNull(index = index)?.takeIf {
                    it.isDigit()
                }?.toString() ?: "",
                enabled = false,
                onValueChange = { value: String ->
                    if (focusRequesters[index].freeFocus()) {
                        val temp = code.toMutableList()
                        if (value == "") {
                            if (temp.size > index) {
                                temp.removeAt(index = index)
                                code = temp
                                focusRequesters.getOrNull(index - 1)?.requestFocus()
                            }
                        } else {
                            if (code.size > index) {
                                temp[index] = value.getOrNull(0) ?: ' '
                            } else {
                                temp.add(value.getOrNull(0) ?: ' ')
                                code = temp
                                focusRequesters.getOrNull(index + 1)?.requestFocus()
                                    ?: onFilled(
                                        code.joinToString(separator = "")
                                    )
                            }
                        }
                    }

                },
                colors = TextFieldDefaults.textFieldColors(
                    backgroundColor = Color.Transparent,
                    disabledIndicatorColor = colorInput
                ),
                visualTransformation = PasswordVisualTransformation()
            )
            if (index != length - 1) {
                Spacer(modifier = Modifier.width(15.dp))
            }
        }

    }
}

@Preview
@Composable
fun OTPtextFieldPrev() {
    MoneyCoTheme {
        OTPTextFields(length = 6, onFilled = {})

    }
}