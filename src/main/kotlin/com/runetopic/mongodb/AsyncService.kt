package com.runetopic.mongodb

import com.mongodb.client.model.CountOptions
import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent
import org.koin.core.component.get
import org.litote.kmongo.EMPTY_BSON
import org.litote.kmongo.coroutine.CoroutineCollection

/**
 * @author Jordan Abraham
 */
open class AsyncService(
    val storage: AsyncStorage
) : KoinComponent {

    suspend inline fun <reified T : Document> add(document: T): Boolean {
        return storage.insertOne(document).wasAcknowledged()
    }

    suspend inline fun <reified T : Document> insertMany(document: Array<T>): Boolean {
        return storage.insertMany(document).wasAcknowledged()
    }

    suspend inline fun <reified T : Document> find(): List<T> {
        return storage.find()
    }

    inline fun <reified T : Document> getCollection(): CoroutineCollection<T> {
        return storage.getCollection()
    }

    suspend inline fun <reified T : Document, R : Comparable<R>> findSorted(crossinline selector: (T) -> R?): List<T> {
        return storage.find<T>().sortedBy(selector)
    }

    suspend inline fun <reified T : Document> exists(filter: Bson): Boolean {
        return storage.findOne<T>(filter) != null
    }

    suspend inline fun <reified T : Document> findBy(filter: Bson): T? {
        return storage.findOne(filter)
    }

    suspend inline fun <reified T : Document> findById(id: String): T? {
        return storage.findOneById(id)
    }

    suspend inline fun <reified T : Document> update(filter: Bson, document: T): Boolean {
        return storage.updateOne(filter, document).wasAcknowledged()
    }

    suspend inline fun <reified T : Document> count(filter: Bson = EMPTY_BSON, options: CountOptions = CountOptions()): Long {
        return storage.countDocuments<T>(filter, options)
    }

    suspend inline fun <reified T : Document> drop(): Void? {
        return storage.drop<T>()
    }
}
