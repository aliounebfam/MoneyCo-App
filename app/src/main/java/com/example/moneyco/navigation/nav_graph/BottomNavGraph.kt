package com.example.moneyco.navigation.nav_graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.screens.TransactionScreen
import com.example.moneyco.screens.main.HomeScreen
import com.example.moneyco.screens.main.ProfilScreen
import com.example.moneyco.screens.main.note.NoteScreen
import kotlinx.coroutines.DelicateCoroutinesApi

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
        composable(route = BottomBarScreen.Note.route) {
            NoteScreen()
        }
        composable(route = BottomBarScreen.Transaction.route) {
            TransactionScreen()
        }
        composable(route = BottomBarScreen.Profil.route) {
            ProfilScreen()
        }


        revenuNavGraph(navController = navController)

    }
}