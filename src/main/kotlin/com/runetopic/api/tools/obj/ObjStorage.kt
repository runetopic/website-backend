package com.runetopic.api.tools.obj

import com.runetopic.api.AsyncStorage
import com.runetopic.api.MongoClient
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineDatabase

/**
 * @author Jordan Abraham
 */
object ObjStorage : KoinComponent, AsyncStorage<Obj> {

    private val mongoClient by inject<MongoClient>()

    override fun database(): CoroutineDatabase = mongoClient.driver.getDatabase("objs")
    override fun collection(): String = "obj"
}
