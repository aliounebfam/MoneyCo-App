package com.example.moneyco.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "users")
data class UserEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val displayName: String? = "",
    val email: String? = "",
    val budget: Int = 0,
    val phoneNumber: String? = "",
    val photoURl: String? = "",
    val isLoggedIn: Boolean = false
)