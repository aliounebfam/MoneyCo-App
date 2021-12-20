package com.example.moneyco.navigation.nav_graph

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.screens.HomeScreen
import com.example.moneyco.screens.ProfilScreen
import com.example.moneyco.screens.TacheScreen
import com.example.moneyco.screens.TransactionScreen

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
        composable(route = BottomBarScreen.Tache.route) {
            TacheScreen()
        }
        composable(route = BottomBarScreen.Transaction.route) {
            TransactionScreen()
        }
        composable(route = BottomBarScreen.Profil.route) {
            ProfilScreen()
        }
    }
}