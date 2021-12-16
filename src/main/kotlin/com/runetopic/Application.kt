package com.runetopic

import com.runetopic.plugins.*
import com.runetopic.plugins.routing.configureRouting
import io.ktor.application.*
import io.ktor.server.netty.*

/**
 * @author Jordan Abraham
 */
fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installDefaultHeaders()
    installLogging()
    installStatusPages()
    installJacksonContentNegotiation()
    installJWT()
    installKoin()
    configureRouting()
}
