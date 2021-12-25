package com.example.moneyco.data

data class User(
    var displayName: String? = "",
    val email: String? = "",
    val budget: Int = 0,
    val phoneNumber: String? = "",
    val photoURl: String? = ""
)
