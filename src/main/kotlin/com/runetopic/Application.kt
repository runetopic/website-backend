package com.runetopic

import com.runetopic.plugins.*
import com.runetopic.plugins.routing.configureRouting
import io.ktor.application.*
import io.ktor.server.netty.*
import java.util.*

fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module() {
    TimeZone.setDefault(TimeZone.getTimeZone("UTC"))
    installDefaultHeaders()
    installCors()
    installLogging()
    installStatusPages()
    installJacksonContentNegotiation()
    installJWT()
    installKoin()
    configureRouting()
}
