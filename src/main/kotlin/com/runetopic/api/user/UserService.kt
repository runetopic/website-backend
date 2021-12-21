package com.runetopic.api.user

import com.runetopic.exception.InvalidUsernameOrPasswordException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
class UserService : KoinComponent {

    private val userStorage by inject<UserStorage>()

    suspend fun add(user: User) = with(userStorage) {
        insertOne(user)
    }

    suspend fun exists(username: String): Boolean = with(userStorage) {
        findOne<User>(User::username eq username) != null
    }

    suspend fun findByUsername(username: String): User = with(userStorage) {
        findOne(User::username eq username) ?: throw InvalidUsernameOrPasswordException()
    }
}
