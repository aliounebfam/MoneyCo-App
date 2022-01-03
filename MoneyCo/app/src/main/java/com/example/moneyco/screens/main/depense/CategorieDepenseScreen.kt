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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.components.TopAppBarWithBack
import com.example.moneyco.navigation.Screen
import com.example.moneyco.screens.main.revenu.components.AddTransactionItem
import com.example.moneyco.screens.main.revenu.components.LoadingAddTransactionItem
import com.jet.firestore.JetFirestore

@Composable
fun CategorieDepenseScreen(navController: NavController) {

    val depenses = remember {
        mutableStateListOf<String>()
    }
    val sousCategories = remember {
        mutableStateListOf<String>()
    }

    val iconsCategorieDepense = listOf(

        Icons.Outlined.Subscriptions,
        Icons.Outlined.CardGiftcard,
        Icons.Outlined.HomeWork,
        Icons.Outlined.EscalatorWarning,
        Icons.Outlined.Chair,
        Icons.Outlined.Feed,
        Icons.Outlined.Checkroom,
        Icons.Outlined.Paid,
        Icons.Outlined.Apartment,
        Icons.Outlined.Celebration,
        Icons.Outlined.PointOfSale,
        Icons.Outlined.HealthAndSafety,
        Icons.Outlined.Traffic,
        Icons.Outlined.TimeToLeave,
        Icons.Outlined.HelpCenter,
    )


    Scaffold(topBar = {
        TopAppBarWithBack(text = "Catégorie dépense", onBackClick = {
            navController.popBackStack()
        })
    }) {
        JetFirestore(
            path = {
                collection("depenses")
            },
            onSingleTimeCollectionFetch = { value, _ ->
                for (document in value!!.documents) {
                    depenses.add(document.get("nom").toString())
                    sousCategories.add(document.get("sous-categories").toString())
                }
            }
        ) {
            if (depenses.size == 0) {
                LoadingAddTransactionItem()
            } else {
                depenses.add("Autres")
                sousCategories.add("false")
                LazyColumn(
                    contentPadding = PaddingValues(
                        15.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    itemsIndexed(items = depenses.distinct()) { index, depense ->
                        AddTransactionItem(text = depense, icon = iconsCategorieDepense[index]) {
                            if (sousCategories[index] == "false") {
                                navController.navigate(
                                    route = Screen.AjouterDepense.passArguments(
                                        depense
                                    )
                                )
                            } else {
                                navController.navigate(
                                    route = Screen.SousCategorieDepense.passCategorie(
                                        depense
                                    )
                                )
                            }
                        }
                    }
                }
            }
        }
    }

}