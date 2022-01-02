package com.example.moneyco.navigation.nav_graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.screens.main.home.HomeScreen
import com.example.moneyco.screens.main.profil.ProfilScreen
import com.example.moneyco.screens.main.transaction.TransactionScreen
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            HomeScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Transaction.route) {
            TransactionScreen(navController = navController)
        }
        composable(route = BottomBarScreen.Profil.route) {
            ProfilScreen(navController = navController)
        }

        revenuNavGraph(navController = navController)
        depenseNavGraph(navController = navController)
    }
}