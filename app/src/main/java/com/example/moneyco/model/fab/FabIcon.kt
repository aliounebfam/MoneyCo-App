package com.example.moneyco.model

import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.vector.ImageVector
import javax.annotation.concurrent.Immutable


@Immutable
interface FabIcon {
    @Stable
    val iconRes: ImageVector
    @Stable
    val iconRotate: Float?
}

private class FabIconImpl(
    override val iconRes: ImageVector,
    override val iconRotate: Float?
) : FabIcon

fun FabIcon(iconRes: ImageVector, iconRotate: Float? = null): FabIcon =
    FabIconImpl(iconRes, iconRotate)