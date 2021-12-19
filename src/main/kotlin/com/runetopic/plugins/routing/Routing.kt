package com.runetopic.plugins.routing

import io.ktor.application.*

/**
 * @author Jordan Abraham
 */
fun Application.configureRouting() {
    configureUserRouting()
    configureRegistrationRouting()
    configureNpcRouting()
    configureTopicRouting()
    configureLoginRouting()
    configureObjRouting()
}
