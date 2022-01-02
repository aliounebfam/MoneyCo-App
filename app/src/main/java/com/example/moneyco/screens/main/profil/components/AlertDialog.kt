package com.example.moneyco.screens.main.profil.components


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.model.signOut
import com.example.moneyco.ui.theme.Nunito
import com.example.moneyco.ui.theme.noir
import kotlinx.coroutines.DelicateCoroutinesApi
import java.util.*


@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@ExperimentalMaterialApi
@ExperimentalAnimationApi
@Composable
fun AlertDialogDeconnexion() {
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


@Composable
fun AlertDialogTransaction(
    mesTransactions: GetMesTransactions
) {
    val openDialog = remember { mutableStateOf(true) }
    if (openDialog.value) {
        AlertDialog(
            onDismissRequest = { openDialog.value = false },
            title = {
                Text(
                    text = if (mesTransactions.type == "revenu") {
                        "Informations du revenu"
                    } else "Informations de la dépense",
                    fontFamily = Nunito,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            },
            text = {
                Column(
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Row {
                        Text(
                            text = "Catégorie : ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                        Text(
                            text = mesTransactions.categorie,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.secondaryVariant,
                        thickness = 1.dp
                    )
                    if (mesTransactions.sousCategorie != " ") {
                        Row {
                            Text(
                                text = "Sous catégorie : ",
                                fontWeight = FontWeight.SemiBold,
                                fontSize = MaterialTheme.typography.body2.fontSize,
                                color = noir
                            )
                            Text(
                                text = mesTransactions.sousCategorie,
                                fontSize = MaterialTheme.typography.body2.fontSize,
                                color = noir
                            )
                        }
                        Divider(
                            color = MaterialTheme.colors.secondaryVariant,
                            thickness = 1.dp
                        )
                    }
                    Row {
                        Text(
                            text = "Montant : ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                        Text(
                            text = mesTransactions.montant,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.secondaryVariant,
                        thickness = 0.8.dp
                    )
                    Row {
                        Text(
                            text = "Type : ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                        Text(
                            text = mesTransactions.type.replaceFirstChar {
                                if (it.isLowerCase()) it.titlecase(
                                    Locale.getDefault()
                                ) else it.toString()
                            },
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.secondaryVariant,
                        thickness = 0.8.dp
                    )
                    Row {
                        Text(
                            text = "Description : ",
                            fontWeight = FontWeight.SemiBold,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                        Text(
                            text = mesTransactions.description,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                    }
                    Divider(
                        color = MaterialTheme.colors.secondaryVariant,
                        thickness = 0.8.dp
                    )
                    Row {
                        Text(
                            text = "Date : ",
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            fontWeight = FontWeight.SemiBold,
                            color = noir
                        )
                        Text(
                            text = mesTransactions.date,
                            fontSize = MaterialTheme.typography.body2.fontSize,
                            color = noir
                        )
                    }

                }

            },
            buttons = {
                Row(
                    modifier = Modifier.padding(
                        start = 20.dp,
                        end = 20.dp,
                        top = 0.dp,
                        bottom = 10.dp
                    ),
                    horizontalArrangement = Arrangement.Center
                ) {
                    Button(
                        onClick = { openDialog.value = false },
                        modifier = Modifier
                            .fillMaxWidth(),
                        elevation = ButtonDefaults.elevation(3.dp),
                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant),
                        shape = RoundedCornerShape(10.dp)
                    ) {
                        Text(
                            text = "OK",
                            modifier = Modifier.padding(
                                top = 3.dp,
                                bottom = 3.dp,
                            )
                        )

                    }
                }
            }
        )
    }
}
