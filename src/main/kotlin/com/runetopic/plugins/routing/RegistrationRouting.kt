package com.runetopic.plugins.routing

import com.runetopic.api.registration.Registration
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.crypto.BCrypt
import com.runetopic.exception.UsernameExistsException
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
fun Application.configureRegistrationRouting() {

    val userService by inject<UserService>()

    routing {
        post("/api/register") {
            val registration = call.receive<Registration>()
            if (userService.exists<User>(User::username eq registration.username)) throw UsernameExistsException()
            val user = User(
                registration.username,
                BCrypt.hashpw(registration.password, BCrypt.gensalt(12)),
                registration.email,
                registration.dateOfBirth
            )
            if (userService.add(user)) call.respond(HttpStatusCode.Created)
        }
    }
}
