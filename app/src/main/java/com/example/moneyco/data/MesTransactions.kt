package com.example.moneyco.data

import com.google.firebase.Timestamp

data class MesTransactions(
    val categorie: String,
    val sousCategorie: String,
    val description: String,
    val montant: Int,
    val type: String,
    val date: Timestamp
)

data class GetMesTransactions(
    val categorie: String,
    val sousCategorie: String,
    val description: String,
    val type: String,
    val montant: String,
    val date: String,
    val id: String
)

