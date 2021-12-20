package com.runetopic.plugins

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.InvalidUsernameOrPasswordException
import com.runetopic.exception.NotFoundException
import com.runetopic.exception.UsernameExistsException
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.response.*

fun Application.installStatusPages() {
    install(StatusPages) {
        exception<NotFoundException> { call.respond(HttpStatusCode.NotFound) }
        exception<InternalServerErrorException> { call.respond(HttpStatusCode.InternalServerError) }
        exception<InvalidUsernameOrPasswordException> { call.respond(HttpStatusCode.Forbidden, "Invalid username and/or password.") }
        exception<UsernameExistsException> { call.respond(HttpStatusCode.Forbidden, "An account with that username already exists.") }
    }
}
