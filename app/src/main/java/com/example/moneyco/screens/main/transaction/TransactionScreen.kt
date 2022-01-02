package com.example.moneyco.screens.main.transaction

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Visibility
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.components.TopAppBarSimple
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.screens.main.revenu.components.LoadingAddTransactionItem
import com.example.moneyco.screens.main.transaction.components.TransactionItem
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import java.text.SimpleDateFormat
import java.util.*

@ExperimentalAnimationApi
@ExperimentalMaterialApi
@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionScreen(navController: NavController) {

    Scaffold(topBar = {
        TopAppBarSimple(text = "Transaction")
    }
    ) {
        var categorie by remember { mutableStateOf("") }
        var sousCategorie by remember { mutableStateOf("") }
        var description by remember { mutableStateOf("") }
        var montant by remember { mutableStateOf("") }
        var date by remember { mutableStateOf("") }
        var type by remember { mutableStateOf("") }
        var id by remember { mutableStateOf("") }

        val mesTransactions = remember {
            mutableStateListOf<GetMesTransactions>()
        }
        val context = LocalContext.current
        val db = Firebase.firestore
        var size by remember { mutableStateOf(10) }
        var budget by remember { mutableStateOf(1) }


        JetFirestore(
            path = {
                collection("users")
                    .document(Firebase.auth.currentUser?.uid.toString())
                    .collection("mesTransactions")
            },
            limitOnSingleTimeCollectionFetch = 10,
            onRealtimeDocumentFetch = { value, _ ->
                budget = value?.get("budget") as Int
            },
            onSingleTimeCollectionFetch = { value, _ ->
                if (value != null) {
                    size = value.size()
                    for (document in value.documents) {
                        montant = document["montant"].toString()
                        type = document["type"].toString()
                        description = document["description"].toString()
                        categorie = document["categorie"].toString()
                        sousCategorie = document["sousCategorie"].toString()
                        id = document.id
                        val sdf = SimpleDateFormat("EEEE dd MMMM yyyy à HH:mm:ss", Locale.FRANCE)
                        val getDate = sdf.format(document.getDate("date"))
                        val affichageDate = getDate.split(' ')
                        date = affichageDate.joinToString(separator = " ") { word ->
                            word.replaceFirstChar {
                                if (it != 'à') {
                                    it.uppercaseChar()
                                } else {
                                    it
                                }
                            }
                        }
                        mesTransactions.add(
                            GetMesTransactions(
                                categorie = categorie,
                                sousCategorie = sousCategorie,
                                description = description,
                                montant = montant,
                                date = date,
                                type = type,
                                id = id
                            )
                        )
                    }
                }
            },
            queryOnCollection = {
                orderBy("date", Query.Direction.DESCENDING)
            },

            ) {
            if (mesTransactions.size == 0) {
                if (budget == 0) {
                    Text("Aucune transaction n'a encore été effectuée")
                }
                LoadingAddTransactionItem(
                    height = 150.dp
                )
            } else {
                Column(
                    Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween

                ) {
                    LazyColumn(
                        contentPadding = PaddingValues(
                            top = 5.dp,
                            start = 5.dp,
                            end = 5.dp,
                            bottom = 60.dp
                        )
                    ) {
                        items(items = mesTransactions.distinct()) { revenu ->
                            TransactionItem(
                                revenu,
                                afficher = true,
                                onDelete = {
                                    db.collection("users")
                                        .document(Firebase.auth.currentUser?.uid.toString())
                                        .collection("mesTransactions")
                                        .document(revenu.id)
                                        .delete()
                                    navController.navigate(BottomBarScreen.Transaction.route)
                                    Toast.makeText(
                                        context,
                                        "Transaction supprimée avec succès",
                                        Toast.LENGTH_SHORT
                                    ).show()
                                })
                        }
                        if (size != 1 && size != 0) {
                            item {
                                Row(
                                    modifier = Modifier.padding(
                                        start = 13.dp,
                                        end = 13.dp,
                                        bottom = 15.dp
                                    )
                                ) {
                                    Button(
                                        onClick = { it.loadNextPage() },
                                        modifier = Modifier.fillMaxWidth(),
                                        elevation = ButtonDefaults.elevation(4.dp),
                                        shape = RoundedCornerShape(10.dp),
                                        colors = ButtonDefaults.buttonColors(MaterialTheme.colors.primaryVariant)
                                    ) {
                                        Text("Voir plus ")
                                        Icon(
                                            imageVector = Icons.Rounded.Visibility,
                                            contentDescription = "oeil"
                                        )
                                    }
                                }
                            }
                        }

                    }

                }

            }
        }
    }
}

