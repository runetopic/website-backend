package com.runetopic

import com.fasterxml.jackson.databind.SerializationFeature
import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import com.runetopic.tools.npc.NpcController
import com.runetopic.tools.npc.npcModule
import com.runetopic.tools.obj.ObjController
import com.runetopic.tools.obj.objModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.netty.*
import org.koin.ktor.ext.Koin

/**
 * @author Jordan Abraham
 */
fun main(args: Array<String>) = EngineMain.main(args)

fun Application.module(testing: Boolean = false) {
    installApplication()
    routing {
        ObjController(this)
        NpcController(this)
    }
}

fun Application.installApplication() {
    install(DefaultHeaders)
    install(CallLogging)
    install(StatusPages) {
        exception<NotFoundException> { call.respond(HttpStatusCode.NotFound) }
        exception<InternalServerErrorException> { call.respond(HttpStatusCode.InternalServerError) }
    }
    install(Koin) {
        modules(
            objModule(),
            npcModule()
        )
    }
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
