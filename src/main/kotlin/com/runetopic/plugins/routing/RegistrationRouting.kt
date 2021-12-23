package com.runetopic.plugins.routing

import com.runetopic.api.registration.Registration
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.exception.UsernameExistsException
import de.mkammerer.argon2.Argon2
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
    val argon2 by inject<Argon2>()

    routing {
        post("/api/register") {
            val registration = call.receive<Registration>()
            if (userService.exists<User>(User::username eq registration.username)) throw UsernameExistsException()
            val user = User(
                username = registration.username,
                password = argon2.hash(12, 65536, 1, registration.password.toCharArray()),
                email = registration.email,
                dateOfBirth = registration.dateOfBirth
            )
            if (userService.add(user)) call.respond(HttpStatusCode.Created)
        }
    }
}
