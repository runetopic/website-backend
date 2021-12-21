package com.runetopic.api

import kotlinx.coroutines.runBlocking
import org.bson.conversions.Bson
import org.litote.kmongo.coroutine.CoroutineDatabase

interface AsyncStorage<T> {
    fun database(): CoroutineDatabase
    fun collection(): String
}

inline fun <reified T : Any> AsyncStorage<T>.insertOneAsync(document: T) = runBlocking { database().getCollection<T>().insertOne(document) }
inline fun <reified T : Any> AsyncStorage<T>.findOneAsync(filter: Bson) = runBlocking { database().getCollection<T>().findOne(filter) }
inline fun <reified T : Any> AsyncStorage<T>.findAllAsync() = runBlocking { database().getCollection<T>().find() }
inline fun <reified T : Any> AsyncStorage<T>.updateOneAsync(filter: Bson, document: T) = runBlocking { database().getCollection<T>().updateOne(filter, document) }
