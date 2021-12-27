package com.example.moneyco.data

import com.google.firebase.Timestamp

data class MesRevenus(
    val categorie: String,
    val sousCategorie: String,
    val description: String,
    val montant: Int,
    val date: Timestamp
)


