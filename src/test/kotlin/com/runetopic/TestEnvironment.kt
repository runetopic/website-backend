package com.runetopic

import io.ktor.application.*
import io.ktor.config.*

object TestEnvironment : (Application) -> Unit {
    private const val JWT_PROPERTY = "jwt.secret"
    const val TEST_KEY = "testSecretKey"

    override fun invoke(application: Application) {
        (application.environment.config as MapApplicationConfig).apply {
            put(JWT_PROPERTY, TEST_KEY)
        }
        application.module()
    }
}
