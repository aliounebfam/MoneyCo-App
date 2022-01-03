package com.example.moneyco.screens.main.revenu

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Scaffold
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.components.TopAppBarWithBack
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.main.revenu.components.AddTransactionItem
import com.example.moneyco.screens.main.revenu.components.LoadingAddTransactionItem
import com.jet.firestore.JetFirestore


@Composable
fun SousCategorieRevenuScreen(navController: NavController, categorie: String) {

    val sousCategories = remember {
        mutableStateListOf<String>()
    }
    val iconsSousCategorieRevenu = mutableListOf<ImageVector>()

    iconsSousCategorieRevenu.addAll(
        when (categorie) {
            "Remboursement" -> listOf(
                Icons.Outlined.MedicalServices,
                Icons.Outlined.Healing,
                Icons.Outlined.HelpCenter,
            )
            "Primes" -> listOf(
                Icons.Outlined.DataSaverOn,
                Icons.Outlined.DateRange,
                Icons.Outlined.HelpCenter,
            )
            "Pensions" -> listOf(
                Icons.Outlined.LocalDining,
                Icons.Outlined.Report,
                Icons.Outlined.Elderly,
                Icons.Outlined.HelpCenter,
            )
            "Intérêts" -> listOf(
                Icons.Outlined.Savings,
                Icons.Outlined.HelpCenter,
            )
            "Allocations" -> listOf(
                Icons.Outlined.EmojiPeople,
                Icons.Outlined.HelpCenter,
            )
            else -> {
                listOf(
                )
            }
        }
    )
    Scaffold(
        topBar = {
            TopAppBarWithBack(text = "Sous catégorie revenu", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        JetFirestore(
            path = {
                collection("revenus")
                    .document(categorie)
                    .collection("sous-categories")
            },

            onSingleTimeCollectionFetch = { value, _ ->
                for (document in value!!.documents) {
                    sousCategories.add(document.get("nom").toString())
                }
            }

        ) {
            if (sousCategories.size == 0) {
                LoadingAddTransactionItem(boucle = 3)
            } else {
                sousCategories.add("Autres")
                LazyColumn(
                    contentPadding = PaddingValues(
                        15.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    itemsIndexed(items = sousCategories.distinct()) { index, sousCategorie ->
                        AddTransactionItem(
                            text = sousCategorie,
                            icon = iconsSousCategorieRevenu[index]
                        ) {
                            navController.navigate(
                                Screen.AjouterRevenu.passArguments(
                                    categorie,
                                    sousCategorie
                                )
                            )
                        }
                    }
                }
            }

        }
    }


}