package com.example.moneyco.di

import android.content.Context
import com.example.moneyco.data.database.MoneyCoDatabase
import com.example.moneyco.data.database.dao.CategoryDao
import com.example.moneyco.data.database.dao.SubCategoryDao
import com.example.moneyco.data.database.dao.TransactionDao
import com.example.moneyco.data.database.dao.UserDao
import com.example.moneyco.data.repository.AuthRepository
import com.example.moneyco.data.repository.CategoryRepository
import com.example.moneyco.data.repository.TransactionRepository
import com.example.moneyco.data.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideMoneyCoDatabase(@ApplicationContext context: Context): MoneyCoDatabase {
        return MoneyCoDatabase.getDatabase(context)
    }

    @Provides
    @Singleton
    fun provideUserDao(database: MoneyCoDatabase): UserDao {
        return database.userDao()
    }

    @Provides
    @Singleton
    fun provideTransactionDao(database: MoneyCoDatabase): TransactionDao {
        return database.transactionDao()
    }

    @Provides
    @Singleton
    fun provideCategoryDao(database: MoneyCoDatabase): CategoryDao {
        return database.categoryDao()
    }

    @Provides
    @Singleton
    fun provideSubCategoryDao(database: MoneyCoDatabase): SubCategoryDao {
        return database.subCategoryDao()
    }

    @Provides
    @Singleton
    fun provideUserRepository(userDao: UserDao): UserRepository {
        return UserRepository(userDao)
    }

    @Provides
    @Singleton
    fun provideTransactionRepository(transactionDao: TransactionDao): TransactionRepository {
        return TransactionRepository(transactionDao)
    }

    @Provides
    @Singleton
    fun provideCategoryRepository(
        categoryDao: CategoryDao,
        subCategoryDao: SubCategoryDao
    ): CategoryRepository {
        return CategoryRepository(categoryDao, subCategoryDao)
    }

    @Provides
    @Singleton
    fun provideAuthRepository(
        @ApplicationContext context: Context,
        userRepository: UserRepository
    ): AuthRepository {
        return AuthRepository(context, userRepository)
    }
}