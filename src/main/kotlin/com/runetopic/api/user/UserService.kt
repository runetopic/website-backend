package com.runetopic.api.user

import com.runetopic.api.findOneAsync
import com.runetopic.api.insertOneAsync
import com.runetopic.exception.InvalidUsernameOrPasswordException
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
object UserService : KoinComponent {

    private val userStorage by inject<UserStorage>()

    fun add(user: User) = runBlocking {
        with(userStorage) {
            insertOneAsync(user)
        }
    }

    fun exists(username: String): Boolean = runBlocking {
        with(userStorage) {
            findOneAsync(User::username eq username) != null
        }
    }

    fun findByUsername(username: String): User = runBlocking {
        with(userStorage) {
            findOneAsync(User::username eq username) ?: throw InvalidUsernameOrPasswordException()
        }
    }
}
