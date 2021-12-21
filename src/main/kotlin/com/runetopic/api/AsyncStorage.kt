package com.runetopic.api

import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineDatabase

interface AsyncStorage<T> {
    fun database(): CoroutineDatabase
    fun collection(): String
}

suspend inline fun <reified T : Any> AsyncStorage<T>.insertOneAsync(document: T) = database().getCollection<T>().insertOne(document)
suspend inline fun <reified T : Any> AsyncStorage<T>.findOneAsync(filter: Bson) = database().getCollection<T>().findOne(filter)
inline fun <reified T : Any> AsyncStorage<T>.findAllAsync() = database().getCollection<T>().find()
suspend inline fun <reified T : Any> AsyncStorage<T>.updateOneAsync(filter: Bson, document: T) = database().getCollection<T>().updateOne(filter, document)
