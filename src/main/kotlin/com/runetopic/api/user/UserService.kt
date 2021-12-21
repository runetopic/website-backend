package com.runetopic.api.user

import com.runetopic.mongodb.AsyncService
import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class UserService(
    storage: AsyncStorage
) : AsyncService(storage)
