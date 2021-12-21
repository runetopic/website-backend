package com.runetopic.api.topics

import com.runetopic.api.AsyncStorage
import com.runetopic.api.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

/**
 * @author Jordan Abraham
 */
object TopicStorage : KoinComponent, AsyncStorage<Topic> {

    private val mongoClient by inject<MongoClient>()

    override fun database(): CoroutineDatabase = mongoClient.driver.getDatabase("topics")
    override fun collection(): String = "topic"
}
