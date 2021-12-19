package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.login.LoginCredentials
import com.runetopic.api.user.UserService
import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureUserRouting() {
    val userService by inject<UserService>()

    routing {
        authenticate(Authentications.LOGGED_IN) {
            get("/api/user/details") {
                val principal = call.principal<JWTPrincipal>()
                val username = principal!!.payload.getClaim("username").asString()
                val user = userService.findByUsername(username)
                call.respond(HttpStatusCode.OK, hashMapOf("username" to user.username, "email" to user.email))
            }
        }
    }
}
