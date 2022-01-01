package com.example.moneyco.navigation.nav_graph

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.example.moneyco.navigation.CATEGORIE
import com.example.moneyco.navigation.DEPENSE_ROUTE
import com.example.moneyco.navigation.SOUS_CATEGORIE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.main.depense.AjouterDepenseScreen
import com.example.moneyco.screens.main.depense.CategorieDepenseScreen
import com.example.moneyco.screens.main.depense.SousCategorieDepenseScreen
import kotlinx.coroutines.DelicateCoroutinesApi


@ExperimentalComposeUiApi
@DelicateCoroutinesApi
fun NavGraphBuilder.depenseNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.CategorieDepense.route,
        route = DEPENSE_ROUTE
    ) {
        composable(
            route = Screen.CategorieDepense.route
        ) {
            CategorieDepenseScreen(navController = navController)
        }

        composable(
            route = Screen.SousCategorieDepense.route,
            arguments = listOf(
                navArgument(CATEGORIE) {
                    type = NavType.StringType
                }
            )
        ) {
            SousCategorieDepenseScreen(
                navController = navController,
                categorie = it.arguments?.getString(CATEGORIE).toString()
            )
        }

        composable(
            route = Screen.AjouterDepense.route,
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
            AjouterDepenseScreen(
                navController = navController,
                categorie = it.arguments?.getString(CATEGORIE).toString(),
                sousCategorie = it.arguments?.getString(SOUS_CATEGORIE).toString()
            )
        }
    }
}