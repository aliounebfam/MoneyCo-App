package com.example.moneyco.components


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import com.example.moneyco.model.signOut
import kotlinx.coroutines.DelicateCoroutinesApi


@DelicateCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun alertDialog() {
    val context = LocalContext.current
    val openDialog = remember { mutableStateOf(true) }

    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = "Déconnexion"
                )
            },
            text = {
                Text(
                    text = "Voulez vous vraiment vous déconnecter ?",
                )
            },

            confirmButton = {

                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color(0xFFEF5350)
                    ),
                    onClick = {
                        signOut(context = context)
                    }) {
                    Text(text = "Oui", color = Color.White)
                }

            },
            dismissButton = {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        backgroundColor = Color.White
                    ),
                    onClick = {
                        openDialog.value = false
                    }) {
                    Text(text = "Non, annuler", color = Color(0xFF1d2424))
                }
            },

            )
    }
}