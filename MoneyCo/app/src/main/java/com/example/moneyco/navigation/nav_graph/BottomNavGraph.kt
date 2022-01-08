package com.example.moneyco.navigation.nav_graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.model.SearchViewModel
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.navigation.ID_DOC
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.main.home.HomeScreen
import com.example.moneyco.screens.main.profil.ProfilScreen
import com.example.moneyco.screens.main.transaction.EditTransactionScreen
import com.example.moneyco.screens.main.transaction.TransactionScreen
import kotlinx.coroutines.DelicateCoroutinesApi

@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun BottomNavGraph(
    navController: NavHostController,
    viewModel: SearchViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {
        composable(route = BottomBarScreen.Home.route) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                HomeScreen(navController = navController)
            }
        }
        composable(route = BottomBarScreen.Transaction.route) {
            TransactionScreen(navController = navController, viewModel = viewModel)
        }
        composable(route = BottomBarScreen.Profil.route) {
            ProfilScreen(navController = navController)
        }

        composable(
            route = Screen.EditerTransaction.route,
            arguments = listOf(
                navArgument(ID_DOC) {
                    type = NavType.StringType
                }
            )
        ) {
            EditTransactionScreen(
                navController = navController,
                idDoc = it.arguments?.getString(
                    ID_DOC
                ).toString()
            )
        }


        revenuNavGraph(navController = navController)
        depenseNavGraph(navController = navController)
    }
}