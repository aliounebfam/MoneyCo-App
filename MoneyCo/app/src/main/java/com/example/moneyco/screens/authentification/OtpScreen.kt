package com.example.moneyco.screens.authentification

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.moneyco.screens.authentification.components.CustomKeyboard
import com.example.moneyco.screens.authentification.components.OtpSecretCodeField
import com.example.moneyco.ui.theme.surface_variant


@ExperimentalMaterialApi
@Composable
fun OtpScreen(
    onClick: (otp: String) -> Unit
) {
    val scrollState = rememberScrollState()
    val inputVal = remember { mutableStateOf("") }


    Column(modifier = Modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .weight(0.1f)
                .fillMaxWidth(1f)
                .verticalScroll(scrollState)
                .padding(
                    start = 20.dp
                ),
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Entrez le code secret",
                color = MaterialTheme.colors.secondaryVariant,
                fontSize = 28.sp,
                fontWeight = FontWeight.Medium
            )
        }

        Column(
            modifier = Modifier
                .verticalScroll(scrollState)
                .weight(0.9f),
            verticalArrangement = Arrangement.SpaceEvenly,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            OtpSecretCodeField(
                length = 6,
                onFilled = {
                },
                inputVal = inputVal,
                modifier = Modifier.padding(20.dp)
            )
            Spacer(modifier = Modifier.height(20.dp))

            Button(
                onClick = { onClick(inputVal.value) },
                modifier = Modifier.fillMaxWidth(0.9f),
                shape = RoundedCornerShape(9.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = MaterialTheme.colors.primaryVariant,
                    disabledBackgroundColor = surface_variant
                ),
                elevation = ButtonDefaults.elevation(
                    8.dp
                ),
                enabled = (inputVal.value.length == 6)

            ) {
                Text(
                    text = "Vérifier le code",
                    modifier = Modifier.padding(
                        vertical = 5.dp
                    )
                )
            }

            Spacer(modifier = Modifier.height(30.dp))

            CustomKeyboard(
                onClick = { digit ->
                    inputVal.value += digit.toString()
                },
                onClick2 = {
                    inputVal.value = inputVal.value.dropLast(1)
                }
            )
        }
    }

}
