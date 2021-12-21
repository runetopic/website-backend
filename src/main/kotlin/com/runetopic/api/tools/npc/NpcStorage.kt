package com.runetopic.api.tools.npc

import com.runetopic.api.AsyncStorage
import com.runetopic.api.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

/**
 * @author Jordan Abraham
 */
object NpcStorage : KoinComponent, AsyncStorage<Npc> {

    private val mongoClient by inject<MongoClient>()

    override fun database(): CoroutineDatabase = mongoClient.driver.getDatabase("npcs")
    override fun collection(): String = "npc"
}
