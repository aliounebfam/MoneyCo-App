package com.example.moneyco.data.repository

import com.example.moneyco.data.User
import com.example.moneyco.data.database.dao.UserDao
import com.example.moneyco.data.database.entities.UserEntity
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(
    private val userDao: UserDao
) {
    fun getCurrentUser(): Flow<User?> {
        return userDao.getCurrentUser().map { userEntity ->
            userEntity?.toUser()
        }
    }

    fun getUserById(userId: Long): Flow<User?> {
        return userDao.getUserById(userId).map { userEntity ->
            userEntity?.toUser()
        }
    }

    suspend fun createUser(user: User): Long {
        return userDao.insertUser(user.toUserEntity())
    }

    suspend fun updateUser(user: User) {
        userDao.updateUser(user.toUserEntity())
    }

    suspend fun updateBudget(userId: Long, budget: Int) {
        userDao.updateBudget(userId, budget)
    }

    suspend fun loginUser(userId: Long) {
        userDao.logoutAllUsers()
        userDao.loginUser(userId)
    }

    suspend fun logoutCurrentUser() {
        userDao.logoutAllUsers()
    }

    // Extension functions to convert between User and UserEntity
    private fun UserEntity.toUser(): User {
        return User(
            displayName = this.displayName,
            email = this.email,
            budget = this.budget,
            phoneNumber = this.phoneNumber,
            photoURl = this.photoURl
        )
    }

    private fun User.toUserEntity(id: Long = 0, isLoggedIn: Boolean = true): UserEntity {
        return UserEntity(
            id = id,
            displayName = this.displayName,
            email = this.email,
            budget = this.budget,
            phoneNumber = this.phoneNumber,
            photoURl = this.photoURl,
            isLoggedIn = isLoggedIn
        )
    }
}