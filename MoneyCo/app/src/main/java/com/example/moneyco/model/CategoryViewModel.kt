package com.example.moneyco.model

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.moneyco.data.database.entities.CategoryEntity
import com.example.moneyco.data.database.entities.SubCategoryEntity
import com.example.moneyco.data.repository.CategoryRepository
import com.example.moneyco.utils.LoadingState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoryViewModel @Inject constructor(
    private val categoryRepository: CategoryRepository
) : ViewModel() {

    private val _categories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val categories: StateFlow<List<CategoryEntity>> = _categories

    private val _expenseCategories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val expenseCategories: StateFlow<List<CategoryEntity>> = _expenseCategories

    private val _incomeCategories = MutableStateFlow<List<CategoryEntity>>(emptyList())
    val incomeCategories: StateFlow<List<CategoryEntity>> = _incomeCategories

    private val _subCategories = MutableStateFlow<List<SubCategoryEntity>>(emptyList())
    val subCategories: StateFlow<List<SubCategoryEntity>> = _subCategories

    private val _categoryState = MutableStateFlow<LoadingState>(LoadingState.IDLE)
    val categoryState: StateFlow<LoadingState> = _categoryState

    init {
        loadAllCategories()
        loadExpenseCategories()
        loadIncomeCategories()
    }

    private fun loadAllCategories() {
        viewModelScope.launch {
            _categoryState.value = LoadingState.LOADING
            try {
                categoryRepository.getAllCategories().collect { categoriesList ->
                    _categories.value = categoriesList
                    _categoryState.value = LoadingState.LOADED
                }
            } catch (e: Exception) {
                _categoryState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    private fun loadExpenseCategories() {
        viewModelScope.launch {
            try {
                categoryRepository.getCategoriesByType("dépense").collect { expenseCategoriesList ->
                    _expenseCategories.value = expenseCategoriesList
                }
            } catch (e: Exception) {
                // Error handling
            }
        }
    }

    private fun loadIncomeCategories() {
        viewModelScope.launch {
            try {
                categoryRepository.getCategoriesByType("revenu").collect { incomeCategoriesList ->
                    _incomeCategories.value = incomeCategoriesList
                }
            } catch (e: Exception) {
                // Error handling
            }
        }
    }

    fun loadSubcategoriesForCategory(categoryId: Long) {
        viewModelScope.launch {
            _categoryState.value = LoadingState.LOADING
            try {
                categoryRepository.getSubcategoriesByCategoryId(categoryId).collect { subcategoriesList ->
                    _subCategories.value = subcategoriesList
                    _categoryState.value = LoadingState.LOADED
                }
            } catch (e: Exception) {
                _categoryState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun addCategory(name: String, type: String) {
        viewModelScope.launch {
            _categoryState.value = LoadingState.LOADING
            try {
                categoryRepository.insertCategory(name, type)
                _categoryState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _categoryState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }

    fun addSubcategory(categoryId: Long, name: String) {
        viewModelScope.launch {
            _categoryState.value = LoadingState.LOADING
            try {
                categoryRepository.insertSubCategory(categoryId, name)
                _categoryState.value = LoadingState.LOADED
            } catch (e: Exception) {
                _categoryState.value = LoadingState.error(e.message ?: "Unknown error")
            }
        }
    }
}