package com.example.moneyco.screens

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.navigation.NavController
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.navigation.nav_graph.BottomNavGraph

@ExperimentalMaterialApi
@Composable
fun MainScreen(navController: NavController) {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomBar(navController = navController)
        }
    ) {
        BottomNavGraph(navController = navController)
    }
}

@Composable
fun BottomBar(navController: NavController) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Transaction,
        BottomBarScreen.Tache,
        BottomBarScreen.Profil,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomNavigation {
        screens.forEach { screen ->
            if (currentDestination != null) {
                AddItem(
                    screen = screen,
                    currentDestination = currentDestination,
                    navController = navController as NavHostController
                )
            }
        }
    }

}


@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination,
    navController: NavHostController
) {
    BottomNavigationItem(
        label = {
            Text(text = screen.title)
        },
        icon = {
            Icon(
                imageVector = screen.icon,
                contentDescription = "Navigation icon"
            )
        },
        selected = currentDestination.hierarchy.any {
            it.route == screen.route
        },
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }

    )
}

