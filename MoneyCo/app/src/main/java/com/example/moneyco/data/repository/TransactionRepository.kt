package com.example.moneyco.data.repository

import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.data.MesTransactions
import com.example.moneyco.data.database.dao.TransactionDao
import com.example.moneyco.data.database.entities.TransactionEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class TransactionRepository @Inject constructor(
    private val transactionDao: TransactionDao
) {
    fun getAllTransactions(userId: Long): Flow<List<GetMesTransactions>> {
        return transactionDao.getTransactionsByUserId(userId).map { transactions ->
            transactions.map { it.toGetMesTransactions() }
        }
    }

    fun getTransactionsByType(userId: Long, type: String): Flow<List<GetMesTransactions>> {
        return transactionDao.getTransactionsByType(userId, type).map { transactions ->
            transactions.map { it.toGetMesTransactions() }
        }
    }

    fun searchTransactions(userId: Long, query: String): Flow<List<GetMesTransactions>> {
        return transactionDao.searchTransactions(userId, query).map { transactions ->
            transactions.map { it.toGetMesTransactions() }
        }
    }

    fun getTransactionsByDateRange(userId: Long, startDate: Date, endDate: Date): Flow<List<GetMesTransactions>> {
        return transactionDao.getTransactionsByDateRange(userId, startDate, endDate).map { transactions ->
            transactions.map { it.toGetMesTransactions() }
        }
    }

    suspend fun addTransaction(userId: Long, transaction: MesTransactions): Long {
        return transactionDao.insertTransaction(transaction.toTransactionEntity(userId))
    }

    suspend fun updateTransaction(userId: Long, transaction: MesTransactions, transactionId: Long) {
        val entity = transaction.toTransactionEntity(userId, transactionId)
        transactionDao.updateTransaction(entity)
    }

    suspend fun deleteTransaction(transactionId: Long) {
        val transaction = transactionDao.getTransactionById(transactionId).map { it }.toString()
        if (transaction.isNotEmpty()) {
            transactionDao.deleteTransaction(TransactionEntity(id = transactionId, userId = 0, categorie = "", sousCategorie = "", description = "", montant = 0, type = "", date = Date()))
        }
    }

    // Extension functions to convert between different transaction types
    private fun TransactionEntity.toGetMesTransactions(): GetMesTransactions {
        val dateFormat = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        return GetMesTransactions(
            categorie = this.categorie,
            sousCategorie = this.sousCategorie,
            description = this.description,
            type = this.type,
            montant = this.montant.toString(),
            date = dateFormat.format(this.date),
            id = this.id.toString()
        )
    }

    private fun MesTransactions.toTransactionEntity(userId: Long, id: Long = 0): TransactionEntity {
        return TransactionEntity(
            id = id,
            userId = userId,
            categorie = this.categorie,
            sousCategorie = this.sousCategorie,
            description = this.description,
            montant = this.montant,
            type = this.type,
            date = this.date.toDate()
        )
    }

    // Convert Firestore Timestamp to Date (during migration)
    private fun com.google.firebase.Timestamp.toDate(): Date {
        return this.toDate()
    }
}