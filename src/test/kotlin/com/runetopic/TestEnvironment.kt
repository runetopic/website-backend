package com.runetopic

import io.ktor.application.*
import io.ktor.config.*

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
