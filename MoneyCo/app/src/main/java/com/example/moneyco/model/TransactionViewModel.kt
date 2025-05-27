package com.example.moneyco.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyco.data.GetMesTransactions
import com.example.moneyco.data.MesTransactions
import com.example.moneyco.data.repository.AuthRepository
import com.example.moneyco.data.repository.TransactionRepository
import com.example.moneyco.data.repository.UserRepository
import com.example.moneyco.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import java.util.Calendar
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TransactionViewModel @Inject constructor(
    private val transactionRepository: TransactionRepository,
    private val userRepository: UserRepository,
    private val authRepository: AuthRepository
) : ViewModel() {

    private val _transactions = MutableStateFlow<List<GetMesTransactions>>(emptyList())
    val transactions: StateFlow<List<GetMesTransactions>> = _transactions

    private val _transactionState = MutableStateFlow<LoadingState>(LoadingState.IDLE)
    val transactionState: StateFlow<LoadingState> = _transactionState

    private val _filteredTransactions = MutableStateFlow<List<GetMesTransactions>>(emptyList())
    val filteredTransactions: StateFlow<List<GetMesTransactions>> = _filteredTransactions

    init {
        loadTransactions()
    }

    private fun loadTransactions() {
        viewModelScope.launch {
            _transactionState.value = LoadingState.LOADING
            try {
                val userId = authRepository.getLoggedInUserId()
                if (userId != -1L) {
                    transactionRepository.getAllTransactions(userId).collect { transactionsList ->
                        _transactions.value = transactionsList
                        _filteredTransactions.value = transactionsList
                        _transactionState.value = LoadingState.LOADED
                    }
                } else {
                    _transactionState.value = LoadingState.error("User not logged in")
                }
            } catch (e: Exception) {
                _transactionState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun filterTransactionsByType(type: String?) {
        viewModelScope.launch {
            if (type == null || type.isEmpty()) {
                _filteredTransactions.value = _transactions.value
                return@launch
            }

            val userId = authRepository.getLoggedInUserId()
            if (userId != -1L) {
                transactionRepository.getTransactionsByType(userId, type).collect { filteredList ->
                    _filteredTransactions.value = filteredList
                }
            }
        }
    }

    fun searchTransactions(query: String) {
        viewModelScope.launch {
            if (query.isEmpty()) {
                _filteredTransactions.value = _transactions.value
                return@launch
            }

            val userId = authRepository.getLoggedInUserId()
            if (userId != -1L) {
                transactionRepository.searchTransactions(userId, query).collect { searchResults ->
                    _filteredTransactions.value = searchResults
                }
            }
        }
    }

    fun addTransaction(transaction: MesTransactions) {
        viewModelScope.launch {
            _transactionState.value = LoadingState.LOADING
            try {
                val userId = authRepository.getLoggedInUserId()
                if (userId != -1L) {
                    transactionRepository.addTransaction(userId, transaction)
                    
                    // Update budget based on transaction type
                    userRepository.getUserById(userId).collect { user ->
                        user?.let {
                            val newBudget = if (transaction.type == "revenu") {
                                it.budget + transaction.montant
                            } else {
                                it.budget - transaction.montant
                            }
                            userRepository.updateBudget(userId, newBudget)
                        }
                    }
                    
                    _transactionState.value = LoadingState.LOADED
                } else {
                    _transactionState.value = LoadingState.error("User not logged in")
                }
            } catch (e: Exception) {
                _transactionState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun updateTransaction(transaction: MesTransactions, transactionId: String) {
        viewModelScope.launch {
            _transactionState.value = LoadingState.LOADING
            try {
                val userId = authRepository.getLoggedInUserId()
                if (userId != -1L) {
                    transactionRepository.updateTransaction(userId, transaction, transactionId.toLong())
                    _transactionState.value = LoadingState.LOADED
                } else {
                    _transactionState.value = LoadingState.error("User not logged in")
                }
            } catch (e: Exception) {
                _transactionState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun deleteTransaction(transactionId: String) {
        viewModelScope.launch {
            _transactionState.value = LoadingState.LOADING
            try {
                transactionRepository.deleteTransaction(transactionId.toLong())
                _transactionState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _transactionState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun getMonthlyTransactions() {
        viewModelScope.launch {
            val userId = authRepository.getLoggedInUserId()
            if (userId != -1L) {
                val calendar = Calendar.getInstance()
                
                // Set to first day of current month
                calendar.set(Calendar.DAY_OF_MONTH, 1)
                calendar.set(Calendar.HOUR_OF_DAY, 0)
                calendar.set(Calendar.MINUTE, 0)
                calendar.set(Calendar.SECOND, 0)
                calendar.set(Calendar.MILLISECOND, 0)
                val startDate = calendar.time
                
                // Set to first day of next month
                calendar.add(Calendar.MONTH, 1)
                val endDate = calendar.time
                
                transactionRepository.getTransactionsByDateRange(userId, startDate, endDate).collect { monthlyTransactions ->
                    _filteredTransactions.value = monthlyTransactions
                }
            }
        }
    }
}