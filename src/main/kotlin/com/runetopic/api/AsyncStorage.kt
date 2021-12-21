package com.runetopic.api

import com.mongodb.client.model.CountOptions
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.EMPTY_BSON

abstract class AsyncStorage<T : Any> : KoinComponent {

    val cached: MutableList<T>
        get() = mutableListOf()

    suspend inline fun <reified T : Any> insertOne(document: T): InsertOneResult {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().insertOne(document)
    }

    suspend inline fun <reified T : Any> findOne(filter: Bson): T? {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().findOne(filter)
    }

    suspend inline fun <reified T : Any> find(): List<T> {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().find().toList()
    }

    suspend inline fun <reified T : Any> updateOne(filter: Bson, document: T): UpdateResult {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().updateOne(filter, document)
    }

    suspend inline fun <reified T : Any> countDocuments(filter: Bson = EMPTY_BSON, options: CountOptions = CountOptions()): Long {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().countDocuments(filter, options)
    }

    suspend inline fun <reified T : Any> drop(): Void? {
        return inject<MongoClient>().value.driver.getDatabase("api").getCollection<T>().drop()
    }
}
