package com.example.moneyco.screens.main.profil.components


import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.model.signOut
import com.example.moneyco.screens.main.transaction.components.ErrorHeader
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

@Composable
fun ErrorDialog(
    title: String,
    desc: String,
    onDismiss: () -> Unit,
    onDelete: () -> Unit
) {
    Dialog(
        onDismissRequest = onDismiss
    ) {
        Box(
            modifier = Modifier
                .width(300.dp)
                .height(400.dp)
        ) {
            Column(
                modifier = Modifier
                    .size(300.dp)
            ) {
                Spacer(modifier = Modifier.height(36.dp))
                Box(
                    modifier = Modifier
                        .width(300.dp)
                        .fillMaxHeight(0.89f)
                        .background(
                            color = Color.White,
                            shape = RoundedCornerShape(10.dp)
                        )
                ) {
                    Column(
                        modifier = Modifier.padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Spacer(modifier = Modifier.height(24.dp))
                        Text(
                            text = title.uppercase(),
                            style = TextStyle(
                                color = Color.Black,
                                fontSize = 18.sp,
                                fontWeight = FontWeight.SemiBold
                            )
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(
                            text = desc,
                            textAlign = TextAlign.Center,
                            style = TextStyle(
                                fontSize = 16.sp,
                                fontWeight = FontWeight.Normal
                            ),
                            fontFamily = Nunito
                        )
                        Spacer(modifier = Modifier.height(24.dp))
                        Row(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            Button(
                                onClick = onDismiss,
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFFEE4435)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Annuler",
                                    color = Color.White
                                )
                            }
                            Spacer(modifier = Modifier.width(8.dp))
                            Button(
                                onClick = onDelete,
                                shape = RoundedCornerShape(0.dp),
                                colors = ButtonDefaults.buttonColors(
                                    backgroundColor = Color(0xFF02CB6F)
                                ),
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .weight(1f)
                                    .clip(RoundedCornerShape(5.dp))
                            ) {
                                Text(
                                    text = "Oui",
                                    color = Color.White
                                )
                            }
                        }
                    }
                }
            }
            ErrorHeader(
                modifier = Modifier
                    .size(65.dp)
                    .align(Alignment.TopCenter)
                    .border(
                        border = BorderStroke(width = 5.dp, color = Color.White),
                        shape = CircleShape
                    )
            )
        }
    }
}
