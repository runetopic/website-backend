package com.runetopic.user

import com.runetopic.exception.InternalServerErrorException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object UserService : KoinComponent {
    private val userStorage by inject<UserStorage>()

    fun add(user: User) = with(userStorage) {
        if (!storage.add(user)) {
            throw InternalServerErrorException()
        }
    }

    fun exists(username: String): Boolean = with(userStorage) {
        storage.any { it.username == username }
    }
}
