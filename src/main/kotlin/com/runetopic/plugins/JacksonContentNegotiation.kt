package com.runetopic.plugins

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.jackson.*
import org.litote.kmongo.id.jackson.IdJacksonModule

fun Application.installJacksonContentNegotiation() {
    install(ContentNegotiation) {
        jackson {
            registerModule(IdJacksonModule())
            registerModule(JavaTimeModule())
            enable(SerializationFeature.INDENT_OUTPUT)
        }
    }
}
