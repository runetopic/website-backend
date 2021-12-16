package com.runetopic.registration

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.UsernameExistsException
import com.runetopic.user.User
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
class RegistrationController(
    routing: Routing
) : KoinComponent {

    init {
        val userService by inject<UserService>()

        with(routing) {
            route("/api/register") {
                post {
                    val registration = call.receive<RegistrationInformation>()
                    if (userService.exists(registration.username)) throw UsernameExistsException()
                    val user = User(
                        registration.username,
                        registration.password,
                        registration.email,
                        registration.dateOfBirth
                    )
                    userService.add(user)
                    call.respond(HttpStatusCode.Created)
                }
            }
        }
    }
}