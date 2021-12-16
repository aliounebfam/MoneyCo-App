package com.example.moneyco.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.Person
import androidx.compose.material.icons.rounded.ReceiptLong
import androidx.compose.material.icons.rounded.Task
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: ImageVector
) {
    object Home : BottomBarScreen(
        route = "home",
        title = "Accueil",
        icon = Icons.Rounded.Home
    )

    object Transaction : BottomBarScreen(
        route = "transaction",
        title = "Transaction",
        icon = Icons.Rounded.ReceiptLong
    )

    object Tache : BottomBarScreen(
        route = "tache",
        title = "Tâche",
        icon = Icons.Rounded.Task
    )

    object Profil : BottomBarScreen(
        route = "profil",
        title = "Profil",
        icon = Icons.Rounded.Person
    )
}
