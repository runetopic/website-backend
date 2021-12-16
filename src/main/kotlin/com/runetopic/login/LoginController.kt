package com.runetopic.login

import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.jwt.loginToken
import com.runetopic.user.UserService
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

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
                    val user = userService.findByUsername(credentials.username)
                    if (user.password != credentials.password) throw InvalidUsernameOrPasswordException()
                    call.respond(HttpStatusCode.OK, hashMapOf("token" to loginToken(credentials.username)))
                }
            }
        }
    }
}
