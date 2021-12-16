package com.runetopic

import com.runetopic.plugins.*
import com.runetopic.plugins.routing.configureRouting
import installCors
import io.ktor.application.*
import io.ktor.server.netty.*

/**
 * @author Jordan Abraham
 */
fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    installDefaultHeaders()
    installCors()
    installLogging()
    installStatusPages()
    installJacksonContentNegotiation()
    installJWT()
    installKoin()
    configureRouting()
}
