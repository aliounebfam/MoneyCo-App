package com.example.moneyco.screens.main.revenu

import android.annotation.SuppressLint
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Paid
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.moneyco.navigation.Screen
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

    JetFirestore(
        path = {
            collection("revenus")
        },
        onRealtimeCollectionFetch = { value, _ ->
            for (document in value!!.documents) {
                revenus.add(document.get("nom").toString())
                sousCategories.add(document.get("sous-categories").toString())
            }
        }
    ) {
        if (revenus.size == 0) {
            Text(text = "Is loading...")
        } else {
            LazyColumn(
                contentPadding = PaddingValues(
                    20.dp
                ),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            )
            {
                itemsIndexed(items = revenus.distinct()) { index, revenu ->
                    Icon(
                        imageVector = Icons.Outlined.Paid,
                        contentDescription = "",
                        tint = Color.Black
                    )
                    Text(text = revenu, modifier = Modifier.clickable {
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
                    })
                }
            }
        }
    }

}