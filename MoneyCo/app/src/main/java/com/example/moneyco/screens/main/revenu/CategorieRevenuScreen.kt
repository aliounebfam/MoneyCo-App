package com.example.moneyco.screens.main.revenu

import android.annotation.SuppressLint
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
import kotlinx.coroutines.DelicateCoroutinesApi

@DelicateCoroutinesApi
@SuppressLint("CoroutineCreationDuringComposition")
@Composable
fun CategorieRevenuScreen(navController: NavController) {

    val revenus = remember {
        mutableStateListOf<String>()
    }
    val sousCategories = remember {
        mutableStateListOf<String>()
    }
    val iconsCategorieRevenu = listOf(
        Icons.Outlined.RequestQuote,
        Icons.Outlined.CardGiftcard,
        Icons.Outlined.Games,
        Icons.Outlined.FamilyRestroom,
        Icons.Outlined.PriceChange,
        Icons.Outlined.Cottage,
        Icons.Outlined.Elderly,
        Icons.Outlined.Savings,
        Icons.Outlined.PointOfSale,
        Icons.Outlined.AttachMoney,
        Icons.Outlined.HelpCenter,
    )
    Scaffold(
        topBar = {
            TopAppBarWithBack(text = "Catégorie revenu", onBackClick = {
                navController.popBackStack()
            })
        }
    ) {
        JetFirestore(
            path = {
                collection("revenus")
            },
            onSingleTimeCollectionFetch = { value, _ ->
                for (document in value!!.documents) {
                    revenus.add(document.get("nom").toString())
                    sousCategories.add(document.get("sous-categories").toString())
                }
            }
        ) {
            if (revenus.size == 0) {
                LoadingAddTransactionItem()
            } else {
                revenus.add("Autres")
                sousCategories.add("false")
                LazyColumn(
                    contentPadding = PaddingValues(
                        15.dp
                    ),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                )
                {
                    itemsIndexed(items = revenus.distinct()) { index, revenu ->
                        AddTransactionItem(text = revenu, icon = iconsCategorieRevenu[index]) {
                            if (sousCategories[index] == "false") {
                                navController.navigate(
                                    route = Screen.AjouterRevenu.passArguments(
                                        revenu
                                    )
                                )
                            } else {
                                navController.navigate(
                                    route = Screen.SousCategorieRevenu.passCategorie(
                                        revenu
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