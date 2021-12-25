package com.example.moneyco.navigation.nav_graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.navigation.MAIN_ROUTE
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.MainScreen
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@ExperimentalCoilApi
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
fun NavGraphBuilder.mainNavGraph(
) {

    navigation(
        startDestination = Screen.MainHome.route,
        route = MAIN_ROUTE
    ) {
        composable(
            route = Screen.MainHome.route
        ) {
            MainScreen()
        }
    }
}