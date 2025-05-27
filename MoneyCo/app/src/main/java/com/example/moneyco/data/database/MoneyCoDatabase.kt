package com.example.moneyco.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.moneyco.data.database.dao.CategoryDao
import com.example.moneyco.data.database.dao.SubCategoryDao
import com.example.moneyco.data.database.dao.TransactionDao
import com.example.moneyco.data.database.dao.UserDao
import com.example.moneyco.data.database.entities.CategoryEntity
import com.example.moneyco.data.database.entities.SubCategoryEntity
import com.example.moneyco.data.database.entities.TransactionEntity
import com.example.moneyco.data.database.entities.UserEntity
import com.example.moneyco.data.database.util.DateConverter

@Database(
    entities = [
        UserEntity::class,
        TransactionEntity::class,
        CategoryEntity::class,
        SubCategoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(DateConverter::class)
abstract class MoneyCoDatabase : RoomDatabase() {

    abstract fun userDao(): UserDao
    abstract fun transactionDao(): TransactionDao
    abstract fun categoryDao(): CategoryDao
    abstract fun subCategoryDao(): SubCategoryDao

    companion object {
        @Volatile
        private var INSTANCE: MoneyCoDatabase? = null

        fun getDatabase(context: Context): MoneyCoDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MoneyCoDatabase::class.java,
                    "moneyco_database"
                )
                    .fallbackToDestructiveMigration()
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}