package com.runetopic.login

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.runetopic.exception.NotFoundException
import com.runetopic.user.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * @author Jordan Abraham
 */
class LoginController(
    routing: Routing
) : KoinComponent {

    init {
        val userService by inject<UserService>()

        with(routing) {
            route("/api/login") {
                post {
                    val credentials = call.receive<LoginCredentials>()
                    if (userService.exists(credentials.username).not()) {
                        throw NotFoundException()
                    }
                    val token = JWT.create()
                        .withClaim("username", credentials.username)
                        .withExpiresAt(Date(System.currentTimeMillis() + 604_800_000)) // 7 Days
                        .sign(Algorithm.HMAC256(System.getenv("JWT-SECRET")))
                    call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
                }
            }
        }
    }
}
