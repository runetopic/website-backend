package com.runetopic

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.fasterxml.jackson.databind.SerializationFeature
import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import com.runetopic.tools.npc.npcModule
import com.runetopic.tools.obj.objModule
import com.runetopic.topics.topicModule
import com.runetopic.user.userModule
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.jackson.*
import io.ktor.response.*
import org.koin.ktor.ext.Koin

/**
 * @author Jordan Abraham
 */
fun Application.installHeaders() = install(DefaultHeaders)

fun Application.installLogging() = install(CallLogging)

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<NotFoundException> { call.respond(HttpStatusCode.NotFound) }
        exception<InternalServerErrorException> { call.respond(HttpStatusCode.InternalServerError) }
    }
}

fun Application.installJackson() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}

fun Application.installJWT() {
    install(Authentication) {
        jwt("logged-in") {
            verifier(JWT.require(Algorithm.HMAC256(System.getenv("JWT-SECRET"))).build())
            validate {
                when {
                    it.payload.getClaim("username").asString() != "" -> JWTPrincipal(it.payload)
                    it.payload.expiresAt.time > System.currentTimeMillis() -> JWTPrincipal(it.payload)
                    else -> null
                }
            }
        }
    }
}

fun Application.installKoin() {
    install(Koin) {
        modules(
            objModule(),
            npcModule(),
            topicModule(),
            userModule()
        )
    }
}
