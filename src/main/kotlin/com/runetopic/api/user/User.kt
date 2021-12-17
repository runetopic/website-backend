package com.runetopic.api.user

/**
 * @author Jordan Abraham
 */
data class User(
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String
)
