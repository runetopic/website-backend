package com.runetopic

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import io.ktor.application.*
import io.ktor.config.*
import org.litote.kmongo.id.jackson.IdJacksonModule

object TestEnvironment : (Application) -> Unit {

    private const val JWT_PROPERTY = "jwt.secret"
    const val JWT_TOKEN = "testSecretKey"

    private const val MONGODB_PROPERTY = "mongodb.driver"
    private const val MONGODB_DRIVER = "mongodb://localhost:27017"

    override fun invoke(application: Application) {
        (application.environment.config as MapApplicationConfig).apply {
            put(JWT_PROPERTY, JWT_TOKEN)
            put(MONGODB_PROPERTY, MONGODB_DRIVER)
        }
        application.module()
    }
}

fun testJacksonObjectMapper(): ObjectMapper = jacksonObjectMapper()
    .registerModule(IdJacksonModule())
    .registerModule(JavaTimeModule())
    .enable(SerializationFeature.INDENT_OUTPUT)
