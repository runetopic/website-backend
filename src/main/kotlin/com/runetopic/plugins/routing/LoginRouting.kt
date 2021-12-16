package com.runetopic.plugins.routing

import com.runetopic.api.login.LoginCredentials
import com.runetopic.api.user.UserService
import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject

fun Application.configureLoginRouting() {

    val userService by inject<UserService>()

    routing {
        post("/api/login") {
            val credentials = call.receive<LoginCredentials>()
            val user = userService.findByUsername(credentials.username)
            if (user.password != credentials.password) throw InvalidUsernameOrPasswordException()
            call.respond(HttpStatusCode.OK, hashMapOf("token" to loginToken(credentials.username)))
        }
    }
}