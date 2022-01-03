package com.example.moneyco.screens.main.transaction

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.model.MyViewModel
import com.example.moneyco.model.SearchViewModel
import com.example.moneyco.navigation.BottomBarScreen
import com.example.moneyco.screens.main.revenu.components.LoadingAddTransactionItem
import com.example.moneyco.screens.main.transaction.components.*
import com.example.moneyco.utils.SearchState
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.SwipeRefreshIndicator
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import java.text.SimpleDateFormat
import java.util.*


@ExperimentalCoilApi
@DelicateCoroutinesApi
@ExperimentalComposeUiApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@SuppressLint("SimpleDateFormat")
@Composable
fun TransactionScreen(navController: NavController, viewModel: SearchViewModel) {
    val searchState by viewModel.searchState
    val searchTextState by viewModel.searchTextState
    val context = LocalContext.current
    var research by remember { mutableStateOf("") }
    var categorie by remember { mutableStateOf("") }
    var sousCategorie by remember { mutableStateOf("") }
    var description by remember { mutableStateOf("") }
    var montant by remember { mutableStateOf("") }
    var date by remember { mutableStateOf("") }
    var type by remember { mutableStateOf("") }
    var id by remember { mutableStateOf("") }
    val db = Firebase.firestore
    var getBudget by remember { mutableStateOf("") }
    val currentUser = Firebase.auth.currentUser
    val docRef = db.collection("users").document(currentUser!!.uid)
    var budget by remember { mutableStateOf(0) }
    var size by remember { mutableStateOf(10) }
    val model: MyViewModel = viewModel()
    val isRefreshing by model.isRefreshing.collectAsState()


    Scaffold(topBar = {
        TopBarTransaction(
            searchState = searchState,
            searchTextState = searchTextState,
            onTextChange = {
                viewModel.updateSearchTextState(it)
                research = it
            },
            onCloseClicked = {
                viewModel.updateSearchState(SearchState.CLOSED)
                research = ""
            },
            onSearchClicked = {
                research = it
            },
            onSearchTriggered = {
                viewModel.updateSearchState(SearchState.OPENED)
            }

        )
    }
    ) {
        if (research.filter { !it.isWhitespace() } == "") {

            val mesTransactions = remember {
                mutableStateListOf<GetMesTransactions>()
            }
//            var size by remember { mutableStateOf(10) }

            JetFirestore(
                path = {
                    collection("users")
                        .document(Firebase.auth.currentUser?.uid.toString())
                        .collection("mesTransactions")
                },
                onSingleTimeCollectionFetch = { value, _ ->
                    if (value != null) {
                        size = value.size()
                        for (document in value.documents) {
                            montant = document["montant"].toString()
                            type = document["type"].toString()
                            description = document["description"].toString()
                            categorie = document["categorie"].toString()
                            sousCategorie = document["sousCategorie"].toString()
                            id = document.id
                            val sdf = SimpleDateFormat(
                                "EEEE dd MMMM yyyy à HH:mm:ss",
                                Locale.FRANCE
                            )
                            val getDate = sdf.format(document.getDate("date"))
                            val affichageDate = getDate.split(' ')
                            date = affichageDate.joinToString(separator = " ") { word ->
                                word.replaceFirstChar {
                                    if (it != 'à') {
                                        it.uppercaseChar()
                                    } else {
                                        it
                                    }
                                }
                            }
                            mesTransactions.add(
                                GetMesTransactions(
                                    categorie = categorie,
                                    sousCategorie = sousCategorie,
                                    description = description,
                                    montant = montant,
                                    date = date,
                                    type = type,
                                    id = id
                                )
                            )
                        }
                    }
                },
                queryOnCollection = {
                    orderBy("date", Query.Direction.DESCENDING)
                },

                ) {
                JetFirestore(path = {
                    collection("users")
                        .document(currentUser.uid)
                },
                    onRealtimeDocumentFetch = { value, _ ->
                        getBudget = value?.get("budget").toString()
                        budget = getBudget.toInt()
                    }
                ) {
                    if (mesTransactions.size == 0) {
                        if (size == 10) {
                            LoadingAddTransactionItem(
                                height = 150.dp
                            )
                        } else if (size == 0) {
                            SwipeRefresh(
                                state = rememberSwipeRefreshState(isRefreshing),
                                onRefresh = { model.refresh() },
                                indicator = { state, trigger ->
                                    SwipeRefreshIndicator(
                                        state = state,
                                        refreshTriggerDistance = trigger,
                                        scale = true,
                                        contentColor = MaterialTheme.colors.primary,
                                        largeIndication = false,
                                        elevation = 8.dp
                                    )
                                }
                            ) {
                                Column(
                                    modifier = Modifier
                                        .fillMaxSize()
                                        .verticalScroll(rememberScrollState()),
                                    horizontalAlignment = Alignment.CenterHorizontally,
                                    verticalArrangement = Arrangement.Center,
                                ) {
                                    TransactionLottieFiles(
                                        modifier = Modifier
                                            .size(350.dp)
                                            .clickable {
                                                Toast
                                                    .makeText(
                                                        context,
                                                        "Pas de transaction effectuée",
                                                        Toast.LENGTH_SHORT
                                                    )
                                                    .show()
                                            }
                                    )
                                }
                            }

                        }


                    } else {
                        Column(
                            Modifier.fillMaxSize()

                        ) {
                            var mesTransactionsFilter: List<GetMesTransactions>
                            var selection by remember {
                                mutableStateOf(0)
                            }
                            TypeChipItem(
                                onClick1 = { selection = 0 },
                                onClick2 = {
                                    selection = 1
                                },
                                onClick3 = {
                                    selection = 2
                                }
                            )
                            if (selection == 1) {
                                mesTransactionsFilter = mesTransactions.filter {
                                    it.type == "dépense"
                                }
                            } else if (selection == 2) {
                                mesTransactionsFilter = mesTransactions.filter {
                                    it.type == "revenu"
                                }
                            } else {
                                mesTransactionsFilter = mesTransactions
                            }
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    start = 5.dp,
                                    end = 5.dp,
                                    bottom = 60.dp
                                )
                            ) {
                                items(items = mesTransactionsFilter.distinct()) { transaction ->
                                    TransactionItem(
                                        transaction,
                                        afficher = true,
                                        onDelete = {
                                            db.collection("users")
                                                .document(Firebase.auth.currentUser?.uid.toString())
                                                .collection("mesTransactions")
                                                .document(transaction.id)
                                                .delete()
                                            if (transaction.type == "revenu") {
                                                budget -= transaction.montant.toInt()
                                            } else {
                                                budget += transaction.montant.toInt()
                                            }
                                            docRef.update("budget", budget.toString())
                                            navController.navigate(BottomBarScreen.Transaction.route)
                                            Toast.makeText(
                                                context,
                                                "Transaction supprimée avec succès",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        })
                                }
                            }

                        }

                    }
                }

            }
        } else {
            val mesTransactionsResearch = remember {
                mutableStateListOf<GetMesTransactions>()
            }

            JetFirestore(
                path = {
                    collection("users")
                        .document(Firebase.auth.currentUser?.uid.toString())
                        .collection("mesTransactions")
                },
                onSingleTimeCollectionFetch = { value, _ ->
                    if (value != null) {
                        size = value.size()
                        for (document in value.documents) {
                            montant = document["montant"].toString()
                            type = document["type"].toString()
                            description = document["description"].toString()
                            categorie = document["categorie"].toString()
                            sousCategorie = document["sousCategorie"].toString()
                            id = document.id
                            val sdf = SimpleDateFormat(
                                "EEEE dd MMMM yyyy à HH:mm:ss",
                                Locale.FRANCE
                            )
                            val getDate = sdf.format(document.getDate("date"))
                            val affichageDate = getDate.split(' ')
                            date = affichageDate.joinToString(separator = " ") { word ->
                                word.replaceFirstChar {
                                    if (it != 'à') {
                                        it.uppercaseChar()
                                    } else {
                                        it
                                    }
                                }
                            }
                            mesTransactionsResearch.add(
                                GetMesTransactions(
                                    categorie = categorie,
                                    sousCategorie = sousCategorie,
                                    description = description,
                                    montant = montant,
                                    date = date,
                                    type = type,
                                    id = id
                                )
                            )
                        }
                    }
                },
                queryOnCollection = {
                    orderBy("date", Query.Direction.DESCENDING)
                },

                ) {
                if (mesTransactionsResearch.size == 0) {
                    if (size == 10) {
                        LoadingAddTransactionItem(
                            height = 150.dp
                        )
                    } else if (size == 0) {
                        SwipeRefresh(
                            state = rememberSwipeRefreshState(isRefreshing),
                            onRefresh = { model.refresh() },
                            indicator = { state, trigger ->
                                SwipeRefreshIndicator(
                                    state = state,
                                    refreshTriggerDistance = trigger,
                                    scale = true,
                                    contentColor = MaterialTheme.colors.primary,
                                    largeIndication = false,
                                    elevation = 8.dp
                                )
                            }
                        ) {
                            Column(
                                modifier = Modifier
                                    .fillMaxSize()
                                    .verticalScroll(rememberScrollState()),
                                horizontalAlignment = Alignment.CenterHorizontally,
                                verticalArrangement = Arrangement.Center,
                            ) {
                                TransactionLottieFiles(
                                    modifier = Modifier
                                        .size(350.dp)
                                        .clickable {
                                            Toast
                                                .makeText(
                                                    context,
                                                    "Pas de transaction effectuée",
                                                    Toast.LENGTH_SHORT
                                                )
                                                .show()
                                        }
                                )
                            }
                        }
                    }

                } else {
                    val resultResearch = mesTransactionsResearch.filter {
                        it.sousCategorie.contains(research.trim(), true) ||
                                it.categorie.contains(research.trim(), true) ||
                                it.description.contains(research.trim(), true) ||
                                it.montant.contains(research.trim(), true) ||
                                it.date.contains(research.trim(), true)
                    }

                    if (resultResearch.size != 0) {
                        Column(
                            Modifier.fillMaxSize(), verticalArrangement = Arrangement.SpaceBetween

                        ) {
                            LazyColumn(
                                contentPadding = PaddingValues(
                                    top = 5.dp,
                                    start = 5.dp,
                                    end = 5.dp,
                                    bottom = 60.dp
                                )
                            ) {
                                items(items = resultResearch.distinct()) { transaction ->
                                    TransactionItem(
                                        transaction,
                                        afficher = true,
                                        onDelete = {
                                            db.collection("users")
                                                .document(Firebase.auth.currentUser?.uid.toString())
                                                .collection("mesTransactions")
                                                .document(transaction.id)
                                                .delete()
                                            if (transaction.type == "revenu") {
                                                budget -= transaction.montant.toInt()
                                            } else {
                                                budget += transaction.montant.toInt()
                                            }
                                            docRef.update("budget", budget.toString())
                                            navController.navigate(BottomBarScreen.Transaction.route)
                                            Toast.makeText(
                                                context,
                                                "Transaction supprimée avec succès",
                                                Toast.LENGTH_SHORT
                                            ).show()
                                        })
                                }
                            }
                        }
                    } else {
                        Column(
                            modifier = Modifier
                                .fillMaxSize()
                                .verticalScroll(rememberScrollState()),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            NoDataLottieFiles(
                                modifier = Modifier
                                    .size(125.dp)
                                    .clickable {
                                        Toast
                                            .makeText(
                                                context,
                                                "Aucune transaction trouvée",
                                                Toast.LENGTH_SHORT
                                            )
                                            .show()
                                    }
                            )
                        }

                    }
                }
            }
        }
    }
}
