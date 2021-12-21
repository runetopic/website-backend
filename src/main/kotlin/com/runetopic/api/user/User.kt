package com.runetopic.api.user

import com.runetopic.mongodb.Document
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class User(
    override val uuid: String = newId<User>().toString(),
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String
) : Document(uuid)
