package com.example.moneyco.screens.main.revenu

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.navigation.Screen
import com.jet.firestore.JetFirestore


@Composable
fun SousCategorieRevenuScreen(navController: NavController, categorie: String) {

    val sous_categories = remember {
        mutableStateListOf<String>()
    }

    JetFirestore(
        path = {
            collection("revenus")
                .document(categorie)
                .collection("sous-categories")
        },

        onRealtimeCollectionFetch = { value, _ ->
            for (document in value!!.documents) {
                sous_categories.add(document.get("nom").toString())
            }
        }

    ) {
        if (sous_categories.size == 0) {
            Text(text = "Sous catégories list loading ...")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                items(items = sous_categories) { sous_categorie ->
                    Text(text = sous_categorie, modifier = Modifier.clickable {
                        navController.navigate(
                            Screen.AjouterRevenu.passArguments(
                                categorie,
                                sous_categorie
                            )
                        )
                    })
                }
            }
        }

    }
}