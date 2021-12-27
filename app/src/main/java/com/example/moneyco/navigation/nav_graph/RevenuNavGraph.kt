package com.example.moneyco.navigation.nav_graph

import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.moneyco.navigation.CATEGORIE
import com.example.moneyco.navigation.REVENU_ROUTE
import com.example.moneyco.navigation.SOUS_CATEGORIE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.main.revenu.AjouterRevenuScreen
import com.example.moneyco.screens.main.revenu.CategorieRevenuScreen
import com.example.moneyco.screens.main.revenu.SousCategorieRevenuScreen
import kotlinx.coroutines.DelicateCoroutinesApi


const val TEST = "ok"

@DelicateCoroutinesApi
fun NavGraphBuilder.revenuNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.CategorieRevenu.route,
        route = REVENU_ROUTE
    ) {
        composable(
            route = Screen.CategorieRevenu.route
        ) {
            CategorieRevenuScreen(navController = navController)
        }

        composable(
            route = Screen.SousCategorieRevenu.route,
            arguments = listOf(
                navArgument(CATEGORIE) {
                    type = NavType.StringType
                }
            )
        ) {
            SousCategorieRevenuScreen(
                navController = navController,
                categorie = it.arguments?.getString(CATEGORIE).toString()
            )
        }

        composable(
            route = Screen.AjouterRevenu.route,
            arguments = listOf(
                navArgument(CATEGORIE) {
                    type = NavType.StringType
                },
                navArgument(SOUS_CATEGORIE) {
                    type = NavType.StringType
                    defaultValue = " "
                }
            )
        ) {
            AjouterRevenuScreen(
                navController = navController,
                categorie = it.arguments?.getString(CATEGORIE).toString(),
                sous_categorie = it.arguments?.getString(SOUS_CATEGORIE).toString()
            )
        }
    }
}