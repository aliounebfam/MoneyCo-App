package com.example.moneyco.data.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.moneyco.data.database.entities.SubCategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SubCategoryDao {
    @Query("SELECT * FROM subcategories WHERE categoryId = :categoryId")
    fun getSubCategoriesByCategoryId(categoryId: Long): Flow<List<SubCategoryEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategories(subCategories: List<SubCategoryEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertSubCategory(subCategory: SubCategoryEntity): Long
}