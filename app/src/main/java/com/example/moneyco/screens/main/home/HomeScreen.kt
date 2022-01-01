package com.example.moneyco.screens.main.home

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.components.TopAppBarSimple
import com.example.moneyco.model.FabIcon
import com.example.moneyco.model.fab.MultiFabItem
import com.example.moneyco.navigation.DEPENSE_ROUTE
import com.example.moneyco.navigation.REVENU_ROUTE
import com.example.moneyco.screens.BottomBar
import com.example.moneyco.screens.main.home.components.MultiFloatingActionButton
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalComposeUiApi
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current
    val currentUser = Firebase.auth.currentUser
    var getBudget by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(0) }
    JetFirestore(
        path = {
            collection("users")
                .document(currentUser?.uid.toString())
        },
        onRealtimeDocumentFetch = { value, _ ->
            getBudget = value?.get("budget").toString()
            budget = getBudget.toInt()
            Log.d("testcase", budget.toString())
        }
    ) {}


    Scaffold(
        topBar = {
            TopAppBarSimple(text = "Accueil")
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
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Votre budget est de : $budget",
                fontSize = MaterialTheme.typography.h6.fontSize,
            )
        }
    }
}