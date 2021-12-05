package com.example.moneyco.navigation.nav_graph

import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.example.moneyco.navigation.AUTH_ROUTE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.LogInScreen
import com.example.moneyco.screens.SignUpScreen

fun NavGraphBuilder.authNavGraph(
    navController: NavHostController
) {
    navigation(
        startDestination = Screen.LogIn.route,
        route = AUTH_ROUTE
    ) {
        composable(
            route = Screen.LogIn.route
        ) {
            LogInScreen(navController = navController)
        }
        composable(
            route = Screen.SignUp.route
        ) {
            SignUpScreen(navController = navController)
        }
    }
}