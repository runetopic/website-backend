package com.runetopic.plugins.routing

import com.runetopic.api.login.LoginCredentials
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.crypto.BCrypt
import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq

fun Application.configureLoginRouting() {

    val secret = environment.config.property("jwt.secret").getString()

    val userService by inject<UserService>()

    routing {
        post("/api/login") {
            val credentials = call.receive<LoginCredentials>()
            val user = userService.findBy<User>(User::username eq credentials.username) ?: throw InvalidUsernameOrPasswordException()
            if (!BCrypt.checkpw(credentials.password, user.password)) throw InvalidUsernameOrPasswordException()
            call.respond(HttpStatusCode.OK, hashMapOf("token" to loginToken(credentials.username, secret)))
        }
    }
}
