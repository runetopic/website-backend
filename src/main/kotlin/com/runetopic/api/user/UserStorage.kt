package com.runetopic.api.user

import com.runetopic.api.AsyncStorage
import com.runetopic.api.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

object UserStorage : KoinComponent, AsyncStorage<User> {

    private val mongoClient by inject<MongoClient>()

    override fun database(): CoroutineDatabase = mongoClient.driver.getDatabase("users")
    override fun collection(): String = "user"
}
