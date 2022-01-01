package com.runetopic

import com.runetopic.plugins.*
import com.runetopic.plugins.routing.configureRouting
import io.ktor.application.*
import java.util.*


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
