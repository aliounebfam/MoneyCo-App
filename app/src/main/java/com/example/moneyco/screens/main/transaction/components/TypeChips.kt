package com.example.moneyco.screens.main.transaction.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.moneyco.ui.theme.noir
import com.example.moneyco.ui.theme.primary
import com.example.moneyco.utils.ChipState

@Composable
fun TypeChips() {

}

@ExperimentalMaterialApi
@Composable
fun TypeChipItem(
    onClick1: () -> Unit,
    onClick2: () -> Unit,
    onClick3: () -> Unit,
) {

    val chip1 = com.example.moneyco.data.Chip("Tout", ChipState.DISABLE)
    val chip2 = com.example.moneyco.data.Chip("Dépense", ChipState.DISABLE)
    val chip3 = com.example.moneyco.data.Chip("Revenu", ChipState.DISABLE)
    var etat1 by remember {
        mutableStateOf(chip1.etat)
    }
    var etat2 by remember {
        mutableStateOf(chip2.etat)
    }
    var etat3 by remember {
        mutableStateOf(chip3.etat)
    }

    if (
        etat2 == ChipState.DISABLE && etat3 == ChipState.DISABLE && etat1 == ChipState.DISABLE
    ) {
        etat1 = ChipState.ENABLE
        onClick1()
    }

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                top = 18.dp,
                bottom = 15.dp,
                start = 14.dp,
                end = 14.dp
            )

    ) {
        Surface(
            border = BorderStroke(
                if (etat1 == ChipState.DISABLE) 1.dp else 0.dp,
                if (etat1 == ChipState.DISABLE) Color.Gray else Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {

                if (etat1 == ChipState.DISABLE) {
                    chip1.etat = ChipState.ENABLE
                    etat1 = ChipState.ENABLE
                    etat2 = ChipState.DISABLE
                    etat3 = ChipState.DISABLE
                    onClick1()

                } else {
                    chip1.etat = ChipState.DISABLE
                    etat1 = ChipState.DISABLE
                }

            },
            color = if (etat1 == ChipState.ENABLE) Color(0xffE8EAF6) else Color.White,
            elevation = 1.dp,

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (etat1 == ChipState.ENABLE) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "done",
                        tint = primary,
                        modifier = Modifier.padding(
                            5.dp
                        )
                    )
                }
                Text(
                    chip1.name,
                    color = if (etat1 == ChipState.ENABLE) primary else noir,
                    modifier = Modifier
                        .padding(
                            end = if (etat1 == ChipState.ENABLE) 17.dp else 17.dp,
                            start = if (etat1 == ChipState.ENABLE) 0.dp else 20.dp,
                            top = if (etat1 == ChipState.ENABLE) 0.dp else 5.dp,
                            bottom = if (etat1 == ChipState.ENABLE) 0.dp else 5.dp,
                        )
                        .alpha(
                            if (etat1 == ChipState.DISABLE) ContentAlpha.medium else 1f
                        ),
                    fontWeight = if (etat1 == ChipState.ENABLE) FontWeight.Bold else FontWeight.SemiBold
                )
            }
        }

        Surface(
            border = BorderStroke(
                if (etat2 == ChipState.DISABLE) 1.dp else 0.dp,
                if (etat2 == ChipState.DISABLE) Color.Gray else Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {

                if (etat2 == ChipState.DISABLE) {
                    chip2.etat = ChipState.ENABLE
                    etat2 = ChipState.ENABLE
                    etat1 = ChipState.DISABLE
                    etat3 = ChipState.DISABLE
                    onClick2()
                } else {
                    chip2.etat = ChipState.DISABLE
                    etat2 = ChipState.DISABLE
                }

            },
            color = if (etat2 == ChipState.ENABLE) Color(0xffE8EAF6) else Color.White,
            elevation = 1.dp,

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (etat2 == ChipState.ENABLE) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "done",
                        tint = primary,
                        modifier = Modifier.padding(
                            5.dp
                        )
                    )
                }
                Text(
                    chip2.name,
                    color = if (etat2 == ChipState.ENABLE) primary else noir,
                    modifier = Modifier
                        .padding(
                            end = if (etat2 == ChipState.ENABLE) 17.dp else 17.dp,
                            start = if (etat2 == ChipState.ENABLE) 0.dp else 20.dp,
                            top = if (etat2 == ChipState.ENABLE) 0.dp else 5.dp,
                            bottom = if (etat2 == ChipState.ENABLE) 0.dp else 5.dp,
                        )
                        .alpha(
                            if (etat2 == ChipState.DISABLE) ContentAlpha.medium else 1f
                        ),
                    fontWeight = if (etat2 == ChipState.ENABLE) FontWeight.Bold else FontWeight.SemiBold
                )
            }
        }
        Surface(
            border = BorderStroke(
                if (etat3 == ChipState.DISABLE) 1.dp else 0.dp,
                if (etat3 == ChipState.DISABLE) Color.Gray else Color.Transparent
            ),
            shape = RoundedCornerShape(8.dp),
            onClick = {

                if (etat3 == ChipState.DISABLE) {
                    chip3.etat = ChipState.ENABLE
                    etat3 = ChipState.ENABLE
                    etat2 = ChipState.DISABLE
                    etat1 = ChipState.DISABLE
                    onClick3()
                } else {
                    chip3.etat = ChipState.DISABLE
                    etat3 = ChipState.DISABLE
                }

            },
            color = if (etat3 == ChipState.ENABLE) Color(0xffE8EAF6) else Color.White,
            elevation = 1.dp,

            ) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                if (etat3 == ChipState.ENABLE) {
                    Icon(
                        imageVector = Icons.Default.Done,
                        contentDescription = "done",
                        tint = primary,
                        modifier = Modifier.padding(
                            5.dp
                        )
                    )
                }
                Text(
                    chip3.name,
                    color = if (etat3 == ChipState.ENABLE) primary else noir,
                    modifier = Modifier
                        .padding(
                            end = if (etat3 == ChipState.ENABLE) 17.dp else 17.dp,
                            start = if (etat3 == ChipState.ENABLE) 0.dp else 20.dp,
                            top = if (etat3 == ChipState.ENABLE) 0.dp else 5.dp,
                            bottom = if (etat3 == ChipState.ENABLE) 0.dp else 5.dp,
                        )
                        .alpha(
                            if (etat3 == ChipState.DISABLE) ContentAlpha.medium else 1f
                        ),
                    fontWeight = if (etat3 == ChipState.ENABLE) FontWeight.Bold else FontWeight.SemiBold
                )
            }
        }
    }
}

//@ExperimentalMaterialApi
//@Preview
//@Composable
//fun TypeChipItemPrev() {
//    val context = LocalContext.current

//}