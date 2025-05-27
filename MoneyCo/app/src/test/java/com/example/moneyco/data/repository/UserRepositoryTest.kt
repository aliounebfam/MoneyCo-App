package com.example.moneyco.data.repository

import com.example.moneyco.data.User
import com.example.moneyco.data.database.dao.UserDao
import com.example.moneyco.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.kotlin.any
import org.mockito.kotlin.verify
import org.mockito.kotlin.whenever

class UserRepositoryTest {
    
    private lateinit var userDao: UserDao
    private lateinit var userRepository: UserRepository
    
    @Before
    fun setup() {
        userDao = mock()
        userRepository = UserRepository(userDao)
    }
    
    @Test
    fun `getCurrentUser returns user with correct mapping`() = runBlocking {
        // Given
        val userEntity = UserEntity(
            id = 1,
            displayName = "Test User",
            email = "test@example.com",
            budget = 5000,
            phoneNumber = "+33123456789",
            photoURl = "https://example.com/photo.jpg",
            isLoggedIn = true
        )
        
        whenever(userDao.getCurrentUser()).thenReturn(flowOf(userEntity))
        
        // When
        val result: Flow<User?> = userRepository.getCurrentUser()
        val user = result.collect { user ->
            // Then
            assertEquals("Test User", user?.displayName)
            assertEquals("test@example.com", user?.email)
            assertEquals(5000, user?.budget)
            assertEquals("+33123456789", user?.phoneNumber)
            assertEquals("https://example.com/photo.jpg", user?.photoURl)
        }
    }
    
    @Test
    fun `createUser correctly maps and inserts user`() = runBlocking {
        // Given
        val user = User(
            displayName = "New User",
            email = "new@example.com",
            budget = 2000,
            phoneNumber = "+33987654321",
            photoURl = "https://example.com/new.jpg"
        )
        
        whenever(userDao.insertUser(any())).thenReturn(2L)
        
        // When
        val result = userRepository.createUser(user)
        
        // Then
        assertEquals(2L, result)
        verify(userDao).insertUser(any())
    }
    
    @Test
    fun `updateBudget calls dao with correct parameters`() = runBlocking {
        // When
        userRepository.updateBudget(1L, 3000)
        
        // Then
        verify(userDao).updateBudget(1L, 3000)
    }
    
    @Test
    fun `loginUser logs out all users first`() = runBlocking {
        // When
        userRepository.loginUser(1L)
        
        // Then
        verify(userDao).logoutAllUsers()
        verify(userDao).loginUser(1L)
    }
}