package com.runetopic.mongodb

import com.mongodb.client.model.CountOptions
import com.mongodb.client.result.InsertManyResult
import com.mongodb.client.result.InsertOneResult
import com.mongodb.client.result.UpdateResult
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.coroutine.CoroutineCollection

/**
 * @author Jordan Abraham
 */
open class AsyncStorage(
    database: String
) : KoinComponent {

    val database = inject<MongoClient>().value.driver.getDatabase(database)

    suspend inline fun <reified T : Document> insertOne(document: T): InsertOneResult {
        return database.getCollection<T>().insertOne(document)
    }

    suspend inline fun <reified T : Document> insertMany(document: Array<T>): InsertManyResult {
        return database.getCollection<T>().insertMany(document.toList())
    }

    suspend inline fun <reified T : Document> findOne(filter: Bson): T? {
        return database.getCollection<T>().findOne(filter)
    }

    suspend inline fun <reified T : Document> findOneById(id: Any): T? {
        return database.getCollection<T>().findOneById(id)
    }

    suspend inline fun <reified T : Document> find(): List<T> {
        return database.getCollection<T>().find().toList()
    }

    inline fun <reified T : Document> getCollection(): CoroutineCollection<T> {
        return database.getCollection()
    }

    suspend inline fun <reified T : Document> updateOne(filter: Bson, document: T): UpdateResult {
        return database.getCollection<T>().updateOne(filter, document)
    }

    suspend inline fun <reified T : Document> countDocuments(filter: Bson, options: CountOptions): Long {
        return database.getCollection<T>().countDocuments(filter, options)
    }

    suspend inline fun <reified T : Document> drop(): Void? {
        return database.getCollection<T>().drop()
    }
}
