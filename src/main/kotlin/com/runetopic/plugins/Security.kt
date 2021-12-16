package com.runetopic.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.runetopic.Authentications
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import java.util.*

fun Application.installJWT() {
    install(Authentication) {
        val secret = environment.config.property("jwt.secret").getString()
        jwt(Authentications.LOGGED_IN) {
            verifier(JWT.require(Algorithm.HMAC256(secret)).build())
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

fun loginToken(username: String): String = JWT.create()
    .withClaim("username", username)
    .withExpiresAt(Date(System.currentTimeMillis() + 604_800_000)) // 7 Days
    .sign(Algorithm.HMAC256(System.getenv("JWT_SECRET")))
