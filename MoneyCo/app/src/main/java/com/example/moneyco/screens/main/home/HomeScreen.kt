package com.example.moneyco.screens.main.home

import android.annotation.SuppressLint
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.rounded.Insights
import androidx.compose.material.icons.rounded.LocalGroceryStore
import androidx.compose.material.icons.rounded.MonetizationOn
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import coil.annotation.ExperimentalCoilApi
import com.example.moneyco.components.TopAppBarSimple
import com.example.moneyco.data.cardItem
import com.example.moneyco.model.FabIcon
import com.example.moneyco.model.fab.MultiFabItem
import com.example.moneyco.navigation.DEPENSE_ROUTE
import com.example.moneyco.navigation.REVENU_ROUTE
import com.example.moneyco.screens.BottomBar
import com.example.moneyco.screens.main.home.components.CardList
import com.example.moneyco.screens.main.home.components.ChartContainer
import com.example.moneyco.screens.main.home.components.MultiFloatingActionButton
import com.example.moneyco.screens.main.transaction.components.NoDataLottieFiles
import com.example.moneyco.ui.theme.Nunito
import com.example.moneyco.ui.theme.primary
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.jet.firestore.JetFirestore
import hu.ma.charts.legend.data.LegendPosition
import hu.ma.charts.pie.PieChart
import hu.ma.charts.pie.data.PieChartData
import hu.ma.charts.pie.data.PieChartEntry
import kotlinx.coroutines.DelicateCoroutinesApi
import java.time.LocalDate
import java.time.ZoneId
import java.time.format.TextStyle
import java.util.*

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("RememberReturnType")
@ExperimentalComposeUiApi
@DelicateCoroutinesApi
@ExperimentalCoilApi
@ExperimentalAnimationApi
@ExperimentalMaterialApi
@Composable
fun HomeScreen(navController: NavController) {
    val currentUser = Firebase.auth.currentUser
    var getBudget by remember { mutableStateOf("") }
    var getmontantDepenses by remember { mutableStateOf("") }
    var getmontantRevenus by remember { mutableStateOf("") }
    var budget by remember { mutableStateOf(0) }
    var montantDepenses by remember { mutableStateOf(0) }
    var montantDepensesMonth by remember { mutableStateOf(0) }
    var montantDepensesYear by remember { mutableStateOf(0) }
    var montantRevenus by remember { mutableStateOf(0) }
    var montantRevenusMonth by remember { mutableStateOf(0) }
    var montantRevenusYear by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val currentDate = LocalDate.now()
    JetFirestore(
        path = {
            collection("users")
                .document(currentUser?.uid.toString())
        },
        onRealtimeDocumentFetch = { value, _ ->
            getBudget = value?.get("budget").toString()
            budget = getBudget.toInt()
        }
    ) {
        JetFirestore(
            path = {
                collection("users")
                    .document(currentUser?.uid.toString())
                    .collection("mesTransactions")
            },
            onRealtimeCollectionFetch = { value, _ ->
                for (document in value!!.documents) {
                    val date = document.getDate("date")!!
                    val testDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                    if (document["type"] == "dépense") {
                        getmontantDepenses = document["montant"].toString()
                        montantDepenses += getmontantDepenses.toInt()
                        if (testDate.monthValue == currentDate.monthValue) {
                            montantDepensesMonth += getmontantDepenses.toInt()
                        }
                        if (testDate.year == currentDate.year) {
                            montantDepensesYear += getmontantDepenses.toInt()
                        }

                    } else {
                        getmontantRevenus = document["montant"].toString()
                        montantRevenus += getmontantRevenus.toInt()
                        if (testDate.monthValue == currentDate.monthValue) {
                            montantRevenusMonth += getmontantRevenus.toInt()
                        }
                        if (testDate.year == currentDate.year) {
                            montantRevenusYear += getmontantRevenus.toInt()
                        }
                    }
                }
            }
        ) {}
    }

    Scaffold(
        topBar = {
            TopAppBarSimple(text = "Accueil")
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        floatingActionButton = {
            MultiFloatingActionButton(
                items = listOf(
                    MultiFabItem(
                        id = 1,
                        iconRes = Icons.Rounded.MonetizationOn,
                        label = "Ajouter revenu",
                        route = REVENU_ROUTE
                    ),
                    MultiFabItem(
                        id = 2,
                        iconRes = Icons.Rounded.LocalGroceryStore,
                        label = "Ajouter dépense",
                        route = DEPENSE_ROUTE
                    )
                ),
                fabIcon = FabIcon(
                    iconRes = Icons.Filled.Add, iconRotate = 45f
                ),
                onFabItemClicked = {
                    navController.navigate(it.route)
                }
            )
        },
        floatingActionButtonPosition = FabPosition.End,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    12.dp
                ),
        ) {
            val cardList = mutableListOf(
                cardItem(
                    "Mon budget",
                    budget,
                    subtitle = "Budget depuis la création du compte",
                    Color(0xffECEFF1)
                ),
                cardItem(
                    "Mes dépenses",
                    montantDepenses,
                    "Somme totale de toutes mes dépenses",
                    Color(0xffFFCDD2)
                ),
                cardItem(
                    "Mes revenus",
                    montantRevenus,
                    "Somme totale de tous mes revenus",
                    Color(0xffC8E6C9)
                )
            )
            CardList(cardList = cardList, navController = navController)
            Spacer(modifier = Modifier.height(30.dp))
            Column(
                verticalArrangement = Arrangement.SpaceBetween
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.padding(
                        bottom = 15.dp
                    )
                ) {
                    Icon(
                        imageVector = Icons.Rounded.Insights,
                        contentDescription = "Connaissances",
                        tint = Color(0xff3F51B5)
                    )
                    Text(
                        text = " Statistiques",
                        fontSize = MaterialTheme.typography.h5.fontSize,
                        color = primary,
                        fontFamily = Nunito
                    )
                }

                var categories = listOf(
                    "Somme totale dépenses",
                    "Somme totale revenus",
                )
                var simpleColors = listOf(
                    Color(0xffEF5350),
                    Color(0xff4CAF50)
                )


                var entries = listOf(montantDepenses.toFloat(), montantRevenus.toFloat())
                if (montantRevenus.toFloat() != 0f || montantDepenses.toFloat() != 0f) {

                    var entriesMonth =
                        listOf(montantDepensesMonth.toFloat(), montantRevenusMonth.toFloat())
                    var categoriesMonth = listOf(
                        "Somme totale dépenses",
                        "Somme totale revenus",
                    )
                    var simpleColorsMonth = listOf(
                        Color(0xffEF5350),
                        Color(0xff4CAF50)
                    )
                    var entriesYear =
                        listOf(montantDepensesYear.toFloat(), montantRevenusYear.toFloat())
                    var categoriesYear = listOf(
                        "Somme totale dépenses",
                        "Somme totale revenus",
                    )
                    var simpleColorsYear = listOf(
                        Color(0xffEF5350),
                        Color(0xff4CAF50)
                    )

                    if (montantDepenses.toFloat() == 0f && montantRevenus.toFloat() != 0f) {
                        entries = listOf(montantRevenus.toFloat())
                        categories = listOf(
                            "Somme totale revenus",
                        )
                        simpleColors = listOf(
                            Color(0xff4CAF50)
                        )
                    }
                    if (montantDepensesMonth.toFloat() == 0f && montantRevenusMonth.toFloat() != 0f) {
                        entriesMonth = listOf(montantRevenusMonth.toFloat())
                        categoriesMonth = listOf(
                            "Somme totale revenus",
                        )
                        simpleColorsMonth = listOf(
                            Color(0xff4CAF50)
                        )
                    }
                    if (montantDepensesYear.toFloat() == 0f && montantRevenusYear.toFloat() != 0f) {
                        entriesYear = listOf(montantRevenusYear.toFloat())
                        categoriesYear = listOf(
                            "Somme totale revenus",
                        )
                        simpleColorsYear = listOf(
                            Color(0xff4CAF50)
                        )
                    }
                    if (montantRevenus.toFloat() == 0f && montantDepenses.toFloat() != 0f) {
                        entries = listOf(montantDepenses.toFloat())
                        categories = listOf(
                            "Somme totale dépenses",
                        )
                        simpleColors = listOf(
                            Color(0xffEF5350)
                        )
                    }
                    if (montantRevenusYear.toFloat() == 0f && montantDepensesYear.toFloat() != 0f) {
                        entriesYear = listOf(montantDepensesYear.toFloat())
                        categoriesYear = listOf(
                            "Somme totale dépenses",
                        )
                        simpleColorsYear = listOf(
                            Color(0xffEF5350)
                        )
                    }
                    if (montantRevenusMonth.toFloat() == 0f && montantDepensesMonth.toFloat() != 0f) {
                        entriesMonth = listOf(montantDepensesMonth.toFloat())
                        categoriesMonth = listOf(
                            "Somme totale dépenses",
                        )
                        simpleColorsMonth = listOf(
                            Color(0xffEF5350)
                        )
                    }

                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(35.dp),
                        contentPadding = PaddingValues(
                            bottom = 115.dp
                        )
                    ) {
                        if (montantRevenusMonth.toFloat() != 0f || montantDepensesMonth.toFloat() != 0f) {
                            item {
                                ChartContainer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 30.dp, top = 2.dp)
                                        .animateContentSize(),
                                    title = "Mois de ${
                                        currentDate.month
                                            .getDisplayName(TextStyle.FULL, Locale.FRANCE)
                                            .replaceFirstChar {
                                                if (it.isLowerCase()) {
                                                    it.titlecase(Locale.getDefault())
                                                } else it.toString()
                                            }
                                    } " +
                                            "${currentDate.year}"
                                ) {
                                    PieChart(
                                        data = PieChartData(
                                            entries = entriesMonth.mapIndexed { idx, value ->
                                                PieChartEntry(
                                                    value = value,
                                                    label = AnnotatedString(categoriesMonth[idx])
                                                )
                                            },
                                            colors = simpleColorsMonth,
                                            legendPosition = LegendPosition.End,
                                            legendShape = CircleShape,
                                        )
                                    )
                                }
                            }
                        }
                        if (montantRevenusYear.toFloat() != 0f || montantDepensesYear.toFloat() != 0f) {
                            item {
                                ChartContainer(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(start = 30.dp, top = 2.dp)
                                        .animateContentSize(),
                                    title = "Année ${currentDate.year}"
                                ) {
                                    PieChart(
                                        data = PieChartData(
                                            entries = entriesYear.mapIndexed { idx, value ->
                                                PieChartEntry(
                                                    value = value,
                                                    label = AnnotatedString(categoriesYear[idx])
                                                )
                                            },
                                            colors = simpleColorsYear,
                                            legendPosition = LegendPosition.End,
                                            legendShape = CircleShape,
                                        )
                                    )
                                }
                            }
                        }

                        item {
                            ChartContainer(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 30.dp, top = 2.dp)
                                    .animateContentSize(),
                                title = "Depuis le début"
                            ) {
                                PieChart(
                                    data = PieChartData(
                                        entries = entries.mapIndexed { idx, value ->
                                            PieChartEntry(
                                                value = value,
                                                label = AnnotatedString(categories[idx])
                                            )
                                        },
                                        colors = simpleColors,
                                        legendPosition = LegendPosition.Bottom,
                                        legendShape = CircleShape,
                                    )
                                )
                            }
                        }

                    }

                } else {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .fillMaxHeight(0.7f),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        NoDataLottieFiles(
                            modifier = Modifier
                                .size(160.dp)
                                .clickable {
                                    Toast
                                        .makeText(
                                            context,
                                            "Aucune transaction effectuée",
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
