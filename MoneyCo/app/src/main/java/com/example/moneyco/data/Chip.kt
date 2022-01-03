package com.example.moneyco.data

import com.example.moneyco.utils.ChipState

data class Chip(
    val name: String,
    var etat: ChipState = ChipState.DISABLE
)
