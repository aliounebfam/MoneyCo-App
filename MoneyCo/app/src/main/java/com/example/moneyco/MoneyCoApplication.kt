package com.example.moneyco

import android.app.Application
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject
import com.example.moneyco.data.repository.CategoryRepository

@HiltAndroidApp
class MoneyCoApplication : Application() {

    @Inject
    lateinit var categoryRepository: CategoryRepository

    private val applicationScope = CoroutineScope(Dispatchers.IO)

    override fun onCreate() {
        super.onCreate()
        
        // Initialize the database with default categories
        initializeDatabase()
    }
    
    private fun initializeDatabase() {
        applicationScope.launch {
            // Add default categories and subcategories
            categoryRepository.insertDefaultCategories()
            categoryRepository.insertDefaultSubcategories()
        }
    }
}