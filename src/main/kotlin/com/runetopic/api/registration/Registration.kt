package com.runetopic.api.registration

/**
 * @author Jordan Abraham
 */
data class Registration(
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String
)
