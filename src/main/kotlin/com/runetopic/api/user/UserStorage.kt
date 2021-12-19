package com.runetopic.api.user

import java.util.*

/**
 * @author Jordan Abraham
 */
object UserStorage {
    val storage: MutableList<User> = Collections.synchronizedList(mutableListOf<User>())
}
