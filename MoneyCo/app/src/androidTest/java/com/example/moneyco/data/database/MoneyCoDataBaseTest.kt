package com.example.moneyco.data.database

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.moneyco.data.database.dao.CategoryDao
import com.example.moneyco.data.database.dao.TransactionDao
import com.example.moneyco.data.database.dao.UserDao
import com.example.moneyco.data.database.entities.CategoryEntity
import com.example.moneyco.data.database.entities.TransactionEntity
import com.example.moneyco.data.database.entities.UserEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException
import java.util.Date

@RunWith(AndroidJUnit4::class)
class MoneyCoDataBaseTest {
    private lateinit var userDao: UserDao
    private lateinit var transactionDao: TransactionDao
    private lateinit var categoryDao: CategoryDao
    private lateinit var db: MoneyCoDatabase

    @Before
    fun createDb() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(
            context, MoneyCoDatabase::class.java
        ).build()
        userDao = db.userDao()
        transactionDao = db.transactionDao()
        categoryDao = db.categoryDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveUser() = runBlocking {
        val user = UserEntity(
            id = 1,
            displayName = "Test User",
            email = "test@example.com",
            budget = 1000,
            isLoggedIn = true
        )
        userDao.insertUser(user)
        
        val retrievedUser = userDao.getUserById(1).first()
        assertEquals(user.displayName, retrievedUser?.displayName)
        assertEquals(user.email, retrievedUser?.email)
        assertEquals(user.budget, retrievedUser?.budget)
        assertEquals(user.isLoggedIn, retrievedUser?.isLoggedIn)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveTransaction() = runBlocking {
        // First insert a user
        val userId = userDao.insertUser(
            UserEntity(
                displayName = "Test User",
                isLoggedIn = true
            )
        )
        
        // Then insert a transaction for this user
        val transaction = TransactionEntity(
            userId = userId,
            categorie = "Alimentation",
            sousCategorie = "Restaurant",
            description = "Dîner",
            montant = 2500,
            type = "dépense",
            date = Date()
        )
        val transactionId = transactionDao.insertTransaction(transaction)
        
        // Retrieve the transaction
        val retrievedTransaction = transactionDao.getTransactionById(transactionId).first()
        assertEquals(transaction.categorie, retrievedTransaction?.categorie)
        assertEquals(transaction.montant, retrievedTransaction?.montant)
        assertEquals(transaction.type, retrievedTransaction?.type)
    }

    @Test
    @Throws(Exception::class)
    fun insertAndRetrieveCategories() = runBlocking {
        val categories = listOf(
            CategoryEntity(name = "Alimentation", type = "dépense"),
            CategoryEntity(name = "Transport", type = "dépense"),
            CategoryEntity(name = "Salaire", type = "revenu")
        )
        categoryDao.insertCategories(categories)
        
        val expenseCategories = categoryDao.getCategoriesByType("dépense").first()
        assertEquals(2, expenseCategories.size)
        
        val incomeCategories = categoryDao.getCategoriesByType("revenu").first()
        assertEquals(1, incomeCategories.size)
    }
}