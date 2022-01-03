package com.example.moneyco.screens.main.home

import android.annotation.SuppressLint
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.data.cardItem
import com.example.moneyco.model.FabIcon
import com.example.moneyco.model.fab.MultiFabItem
import com.example.moneyco.navigation.DEPENSE_ROUTE
import com.example.moneyco.navigation.REVENU_ROUTE
import com.example.moneyco.screens.BottomBar
import com.example.moneyco.screens.main.home.components.CardList
import com.example.moneyco.screens.main.home.components.MultiFloatingActionButton
import com.example.moneyco.ui.theme.Nunito
import com.example.moneyco.ui.theme.primary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import kotlinx.coroutines.DelicateCoroutinesApi

@SuppressLint("RememberReturnType")
@ExperimentalComposeUiApi
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser
    var getBudget by remember { mutableStateOf("") }
    var getmontantDepenses by remember { mutableStateOf("") }
    var getmontantRevenus by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(0) }
    var montantDepenses by remember { mutableStateOf(0) }
    var montantRevenus by remember { mutableStateOf(0) }
    JetFirestore(
        path = {
            collection("users")
                .document(currentUser?.uid.toString())
        },
        onRealtimeDocumentFetch = { value, _ ->
            getBudget = value?.get("budget").toString()
            budget = getBudget.toInt()
        }
    ) {
        JetFirestore(
            path = {
                collection("users")
                    .document(currentUser?.uid.toString())
                    .collection("mesTransactions")
            },
            onRealtimeCollectionFetch = { value, _ ->
                for (document in value!!.documents) {
                    if (document["type"] == "dépense") {
                        getmontantDepenses = document["montant"].toString()
                        montantDepenses += getmontantDepenses.toInt()
                    } else {
                        getmontantRevenus = document["montant"].toString()
                        montantRevenus += getmontantRevenus.toInt()
                    }
                }
            }
        ) {}
    }

    var darkMode by remember {
        mutableStateOf(false)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = "Accueil",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        fontWeight = FontWeight.SemiBold,
                        fontFamily = Nunito,
                        fontSize = 22.sp
                    )
                },
                backgroundColor = MaterialTheme.colors.primaryVariant,
                elevation = 10.dp,
                actions = {
                    IconButton(onClick = { darkMode = !darkMode }) {
                        Icon(
                            imageVector = if (!darkMode) {
                                Icons.Rounded.DarkMode
                            } else Icons.Rounded.LightMode,
                            contentDescription = "dark mode",
                            tint = Color.White,
                            modifier = Modifier.size(30.dp)
                        )
                    }
                }
            )
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            MultiFloatingActionButton(
                items = listOf(
                    MultiFabItem(
                        id = 1,
                        iconRes = Icons.Rounded.MonetizationOn,
                        label = "Ajouter revenu",
                        route = REVENU_ROUTE
                    ),
                    MultiFabItem(
                        id = 2,
                        iconRes = Icons.Rounded.LocalGroceryStore,
                        label = "Ajouter dépense",
                        route = DEPENSE_ROUTE
                    )
                ),
                fabIcon = FabIcon(
                    iconRes = Icons.Filled.Add, iconRotate = 45f
                ),
                onFabItemClicked = {
                    navController.navigate(it.route)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(
                    12.dp
                ),
//            horizontalAlignment = Alignment.CenterHorizontally,
//            verticalArrangement = Arrangement.SpaceBetween
        ) {
            val cardList = mutableListOf(
                cardItem(
                    "Mon budget",
                    budget,
                    "Budget depuis la création du compte",
                    Color(0xffECEFF1)
                ),
                cardItem(
                    "Mes dépenses",
                    montantDepenses,
                    "Somme totale de toutes mes dépenses",
                    Color(0xffFFCDD2)
                ),
                cardItem(
                    "Mes revenus",
                    montantRevenus,
                    "Somme totale de tous mes revenus",
                    Color(0xffC8E6C9)
                )
            )
            CardList(cardList = cardList)
            Spacer(modifier = Modifier.height(35.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Insights,
                        contentDescription = "Connaissances"
                    )
                    Text(
                        text = " Statistiques",
                        fontSize = MaterialTheme.typography.h6.fontSize,
                        color = primary,
                        fontFamily = Nunito
                    )
                }

            }

        }
    }
}