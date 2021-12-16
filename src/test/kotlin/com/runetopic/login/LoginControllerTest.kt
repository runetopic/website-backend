package com.runetopic.login

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.module
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class LoginControllerTest {

    @Test
    fun `test login user does not exist`() {
        val credentials = mockk<LoginCredentials>()
        every { credentials.username } returns "Jordan"
        every { credentials.password } returns "passwordtest"

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/api/login") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(credentials))
            }.apply {
                assertEquals(
                    HttpStatusCode.Forbidden,
                    response.status()
                )
                assertEquals(
                    response.content,
                    "Invalid username and/or password."
                )
            }
        }
    }
}