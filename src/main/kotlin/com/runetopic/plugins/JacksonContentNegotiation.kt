package com.runetopic.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import org.litote.kmongo.id.jackson.IdJacksonModule

fun Application.installJacksonContentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            registerModule(IdJacksonModule())
        }
    }
}
