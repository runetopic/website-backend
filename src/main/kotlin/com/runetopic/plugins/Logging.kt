package com.runetopic.plugins

import io.ktor.application.*
import io.ktor.features.*

fun Application.installLogging() = install(CallLogging)