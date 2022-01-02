package com.example.moneyco.navigation.nav_graph

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.model.SearchViewModel
import com.example.moneyco.navigation.AUTH_ROUTE
import com.example.moneyco.navigation.MAIN_ROUTE
import com.example.moneyco.navigation.ROOT_ROUTE
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.DelicateCoroutinesApi


@ExperimentalComposeUiApi
@ExperimentalCoilApi
@DelicateCoroutinesApi
@RequiresApi(Build.VERSION_CODES.M)
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun SetupNavGraph(
    navController: NavHostController,
    viewModel: SearchViewModel
) {

    val auth = Firebase.auth

    val start: String = if (auth.currentUser != null) {
        MAIN_ROUTE
    } else AUTH_ROUTE


    NavHost(
        navController = navController,
        startDestination = start,
        route = ROOT_ROUTE
    ) {
        authNavGraph(navController = navController)
        mainNavGraph(viewModel = viewModel)
    }
}