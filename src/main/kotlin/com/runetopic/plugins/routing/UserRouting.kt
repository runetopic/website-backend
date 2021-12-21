package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.exception.InvalidUsernameOrPasswordException
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq

fun Application.configureUserRouting() {

    val userService by inject<UserService>()

    routing {
        authenticate(Authentications.LOGGED_IN) {
            get("/api/user/details") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val user = userService.findBy<User>(User::username eq username) ?: throw InvalidUsernameOrPasswordException()
                call.respond(HttpStatusCode.OK, mapOf("username" to user.username, "email" to user.email))
            }
        }
    }
}
