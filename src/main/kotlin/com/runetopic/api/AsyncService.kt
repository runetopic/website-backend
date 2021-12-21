package com.runetopic.api

import org.bson.conversions.Bson
import org.koin.core.component.KoinComponent

/**
 * @author Jordan Abraham
 */
abstract class AsyncService<T : AsyncStorage<*>> : KoinComponent {

    abstract fun storage(): T

    suspend inline fun <reified T : Any> add(document: T): Boolean = storage().insertOne(document).wasAcknowledged()
    suspend inline fun <reified T : Any> find(): List<T> = storage().find()
    suspend inline fun <reified T : Any, R : Comparable<R>> findSorted(crossinline selector: (T) -> R?): List<T> = storage().find<T>().sortedBy(selector)
    suspend inline fun <reified T : Any> exists(filter: Bson): Boolean = storage().findOne<T>(filter) != null
    suspend inline fun <reified T : Any> findBy(filter: Bson): T? = storage().findOne(filter)
    suspend inline fun <reified T : Any> update(filter: Bson, document: T): Boolean = storage().updateOne(filter, document).wasAcknowledged()
}
