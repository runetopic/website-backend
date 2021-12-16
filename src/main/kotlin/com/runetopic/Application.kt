package com.runetopic

import io.ktor.application.*
import io.ktor.server.netty.*

/**
 * @author Jordan Abraham
 */
fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    installHeaders()
    installLogging()
    installStatusPages()
    installJackson()
    installJWT()
    installKoin()
    configureRouting()
}
