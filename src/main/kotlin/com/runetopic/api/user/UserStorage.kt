package com.runetopic.api.user

import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class UserStorage(
    database: String
) : AsyncStorage(database)
