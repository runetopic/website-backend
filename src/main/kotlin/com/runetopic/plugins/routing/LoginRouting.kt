package com.runetopic.plugins.routing

import com.runetopic.api.login.LoginCredentials
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.plugins.loginToken
import de.mkammerer.argon2.Argon2
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
    val argon2 by inject<Argon2>()

    routing {
        post("/api/login") {
            val credentials = call.receive<LoginCredentials>()
            val user = userService.findBy<User>(User::username eq credentials.username) ?: throw InvalidUsernameOrPasswordException()
            if (!argon2.verify(user.password, credentials.password.toCharArray())) throw InvalidUsernameOrPasswordException()
            call.respond(HttpStatusCode.OK, hashMapOf("token" to loginToken(credentials.username, secret)))
        }
    }
}
