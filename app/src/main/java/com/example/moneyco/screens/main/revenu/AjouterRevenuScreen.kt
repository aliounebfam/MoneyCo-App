package com.example.moneyco.screens.main.revenu

import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import com.example.moneyco.data.MesRevenus
import com.example.moneyco.model.enregistrerMesRevenus
import com.google.firebase.Timestamp

@Composable
fun AjouterRevenuScreen(
    navController: NavController,
    categorie: String,
    sous_categorie: String
) {

    Text("categorie : $categorie et sous catégorie : $sous_categorie")


    enregistrerMesRevenus(
        MesRevenus(
            categorie,
            sous_categorie,
            "voici un premier enregistrement",
            150,
            Timestamp.now()
        )
    )
}