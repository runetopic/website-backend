package com.runetopic.api

import com.mongodb.client.model.CountOptions
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.EMPTY_BSON

abstract class AsyncStorage<T : Any> : KoinComponent {

    val database = inject<MongoClient>().value.driver.getDatabase("api")

    suspend inline fun <reified T : Any> insertOne(document: T): InsertOneResult {
        return database.getCollection<T>().insertOne(document)
    }

    suspend inline fun <reified T : Any> findOne(filter: Bson): T? {
        return database.getCollection<T>().findOne(filter)
    }

    suspend inline fun <reified T : Any> find(): List<T> {
        return database.getCollection<T>().find().toList()
    }

    suspend inline fun <reified T : Any> updateOne(filter: Bson, document: T): UpdateResult {
        return database.getCollection<T>().updateOne(filter, document)
    }

    suspend inline fun <reified T : Any> countDocuments(filter: Bson = EMPTY_BSON, options: CountOptions = CountOptions()): Long {
        return database.getCollection<T>().countDocuments(filter, options)
    }

    suspend inline fun <reified T : Any> drop(): Void? {
        return database.getCollection<T>().drop()
    }
}
