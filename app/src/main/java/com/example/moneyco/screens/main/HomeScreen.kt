package com.example.moneyco.screens.main

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.components.fab.MultiFloatingActionButton
import com.example.moneyco.model.FabIcon
import com.example.moneyco.model.fab.MultiFabItem
import com.example.moneyco.model.signOut
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.navigation.REVENU_ROUTE
import com.example.moneyco.screens.BottomBar
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(navController: NavController) {
    val context = LocalContext.current

    Scaffold(
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
                        route = BottomBarScreen.Profil.route
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
                .background(Color.Red),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text(
                text = "Accueil",
                fontSize = MaterialTheme.typography.h3.fontSize,
                fontWeight = FontWeight.Bold,
                color = Color.White
            )
            Button(onClick = {
                signOut(context = context)
            }) {
                Text(text = "Sign OUT")
            }
        }
    }
}

