package com.example.moneyco.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moneyco.navigation.MAIN_ROUTE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.MainScreen

fun NavGraphBuilder.mainNavGraph(
    navController: NavHostController
) {

    navigation(
        startDestination = Screen.MainHome.route,
        route = MAIN_ROUTE
    ) {
        composable(
            route = Screen.MainHome.route

        ) {
            MainScreen(navController = navController)
        }
    }
}