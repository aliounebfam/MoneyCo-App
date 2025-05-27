package com.example.moneyco.data.repository

import com.example.moneyco.data.database.dao.CategoryDao
import com.example.moneyco.data.database.dao.SubCategoryDao
import com.example.moneyco.data.database.entities.CategoryEntity
import com.example.moneyco.data.database.entities.SubCategoryEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CategoryRepository @Inject constructor(
    private val categoryDao: CategoryDao,
    private val subCategoryDao: SubCategoryDao
) {
    // Categories
    fun getCategoriesByType(type: String): Flow<List<CategoryEntity>> {
        return categoryDao.getCategoriesByType(type)
    }

    fun getAllCategories(): Flow<List<CategoryEntity>> {
        return categoryDao.getAllCategories()
    }

    suspend fun insertCategory(name: String, type: String): Long {
        val category = CategoryEntity(name = name, type = type)
        return categoryDao.insertCategory(category)
    }

    suspend fun insertDefaultCategories() {
        val defaultExpenseCategories = listOf(
            CategoryEntity(name = "Alimentation", type = "dépense"),
            CategoryEntity(name = "Transport", type = "dépense"),
            CategoryEntity(name = "Logement", type = "dépense"),
            CategoryEntity(name = "Loisirs", type = "dépense"),
            CategoryEntity(name = "Santé", type = "dépense"),
            CategoryEntity(name = "Éducation", type = "dépense"),
            CategoryEntity(name = "Vêtements", type = "dépense"),
            CategoryEntity(name = "Cadeaux", type = "dépense"),
            CategoryEntity(name = "Autre", type = "dépense")
        )

        val defaultIncomeCategories = listOf(
            CategoryEntity(name = "Salaire", type = "revenu"),
            CategoryEntity(name = "Investissement", type = "revenu"),
            CategoryEntity(name = "Cadeau", type = "revenu"),
            CategoryEntity(name = "Remboursement", type = "revenu"),
            CategoryEntity(name = "Autre", type = "revenu")
        )

        categoryDao.insertCategories(defaultExpenseCategories + defaultIncomeCategories)
    }

    // Subcategories
    fun getSubcategoriesByCategoryId(categoryId: Long): Flow<List<SubCategoryEntity>> {
        return subCategoryDao.getSubCategoriesByCategoryId(categoryId)
    }

    suspend fun insertSubCategory(categoryId: Long, name: String): Long {
        val subCategory = SubCategoryEntity(categoryId = categoryId, name = name)
        return subCategoryDao.insertSubCategory(subCategory)
    }

    suspend fun insertDefaultSubcategories() {
        // This would be implemented with default subcategories for each category
        // For example:
        val foodSubcategories = listOf(
            SubCategoryEntity(categoryId = 1, name = "Supermarché"),
            SubCategoryEntity(categoryId = 1, name = "Restaurant"),
            SubCategoryEntity(categoryId = 1, name = "Café")
        )
        
        // More subcategories would be defined here for other categories
        
        subCategoryDao.insertSubCategories(foodSubcategories)
    }
}