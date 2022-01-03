package com.example.moneyco.screens.main.depense

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
fun SousCategorieDepenseScreen(navController: NavController, categorie: String) {

    val sousCategories = remember {
        mutableStateListOf<String>()
    }
    val iconsSousCategorieDepense = mutableListOf<ImageVector>()

    //meubles
//

    iconsSousCategorieDepense.addAll(
        when (categorie) {
            "Abonnements" -> listOf(
                Icons.Outlined.Tv,
                Icons.Outlined.Delete,
                Icons.Outlined.Wifi,
                Icons.Outlined.HelpCenter,
            )
            "Cadeau" -> listOf(
                Icons.Outlined.Cake,
                Icons.Outlined.Liquor,
                Icons.Outlined.HelpCenter,
            )
            "Dépenses Ménagères" -> listOf(
                Icons.Outlined.LunchDining,
                Icons.Outlined.Soap,
                Icons.Outlined.HelpCenter,
            )
            "Enfants" -> listOf(
                Icons.Outlined.ContactPage,
                Icons.Outlined.ChildCare,
                Icons.Outlined.School,
                Icons.Outlined.HelpCenter,
            )
            "Equipement" -> listOf(
                Icons.Outlined.Microwave,
                Icons.Outlined.Weekend,
                Icons.Outlined.DriveEta,
                Icons.Outlined.HelpCenter,
            )
            "Factures" -> listOf(
                Icons.Outlined.Water,
                Icons.Outlined.OfflineBolt,
                Icons.Outlined.HelpCenter,
            )
            "Habillement" -> listOf(
                Icons.Outlined.Checkroom,
                Icons.Outlined.HelpCenter,
            )
            "Impôts" -> listOf(
                Icons.Outlined.LocalAtm,
                Icons.Outlined.LocalActivity,
                Icons.Outlined.HelpCenter,
            )
            "Location" -> listOf(
                Icons.Outlined.LocationCity,
                Icons.Outlined.HelpCenter,
            )
            "Loisirs" -> listOf(
                Icons.Outlined.Festival,
                Icons.Outlined.Liquor,
                Icons.Outlined.RestaurantMenu,
                Icons.Outlined.Snowboarding,
                Icons.Outlined.LocalAirport,
                Icons.Outlined.HelpCenter,
            )
            "Santé" -> listOf(
                Icons.Outlined.MedicalServices,
                Icons.Outlined.Medication,
                Icons.Outlined.HelpCenter,
            )
            "Transport" -> listOf(
                Icons.Outlined.DirectionsBus,
                Icons.Outlined.LocalTaxi,
                Icons.Outlined.HelpCenter,
            )
            "Voiture personnelle" -> listOf(
                Icons.Outlined.Opacity,
                Icons.Outlined.Shower,
                Icons.Outlined.CarRepair,
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
            TopAppBarWithBack(text = "Sous catégorie dépense", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        JetFirestore(
            path = {
                collection("depenses")
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
                            icon = iconsSousCategorieDepense[index]
                        ) {
                            navController.navigate(
                                Screen.AjouterDepense.passArguments(
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