package dev.logickoder.synote.auth.api

data class User(
    val id: UserId,
    val name: String,
    val email: String,
)
