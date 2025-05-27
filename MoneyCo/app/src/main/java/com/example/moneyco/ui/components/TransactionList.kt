package com.example.moneyco.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.screens.main.transaction.components.TransactionItem
import com.example.moneyco.utils.LoadingState

@Composable
fun TransactionList(
    transactions: List<GetMesTransactions>,
    loadingState: LoadingState,
    onDeleteClick: (String) -> Unit,
    onEditClick: (GetMesTransactions) -> Unit
) {
    when (loadingState) {
        LoadingState.LOADING -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }
        is LoadingState.ERROR -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(text = (loadingState as LoadingState.ERROR).message)
            }
        }
        else -> {
            if (transactions.isEmpty()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(text = "Aucune transaction trouvée")
                }
            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                ) {
                    items(transactions) { transaction ->
                        TransactionItem(
                            transaction = transaction,
                            onDeleteClick = { onDeleteClick(transaction.id) },
                            onEditClick = { onEditClick(transaction) }
                        )
                    }
                }
            }
        }
    }
}