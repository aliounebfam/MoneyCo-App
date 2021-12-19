package com.example.moneyco.model.fab

import androidx.compose.ui.graphics.vector.ImageVector

data class MultiFabItem(
    val id: Int,
    val iconRes: ImageVector,
    val label: String = "",
    val route: String = ""
)
