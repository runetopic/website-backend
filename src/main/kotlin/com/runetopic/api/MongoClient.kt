package com.runetopic.api

import com.mongodb.ConnectionString
import com.mongodb.MongoClientSettings
import io.ktor.application.*
import org.bson.UuidRepresentation
import org.koin.core.component.KoinComponent
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

/**
 * @author Jordan Abraham
 */
class MongoClient(
    application: Application
) : KoinComponent {
    val driver = KMongo.createClient(
        MongoClientSettings.builder()
            .uuidRepresentation(UuidRepresentation.STANDARD)
            .applyConnectionString(ConnectionString(application.environment.config.property("mongodb.driver").getString()))
            .build()
    ).coroutine
}
