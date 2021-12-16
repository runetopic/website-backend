package com.runetopic.user

import java.util.*

/**
 * @author Jordan Abraham
 */
object UserStorage {
    val storage = Collections.synchronizedList(mutableListOf<User>())
}
