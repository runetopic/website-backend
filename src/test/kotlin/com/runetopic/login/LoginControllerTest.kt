package com.runetopic.login

import com.runetopic.TestEnvironment
import com.runetopic.api.login.LoginCredentials
import com.runetopic.testJacksonObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.every
import io.mockk.mockk
import org.slf4j.Marker
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime

/**
 * @author Jordan Abraham
 */
class LoginControllerTest {

    @Test
    fun `test login user does not exist`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val credentials = mockk<LoginCredentials>()
            every { credentials.username } returns "Jordan"
            every { credentials.password } returns "passwordtest"

            with(
                handleRequest(HttpMethod.Post, "/api/login") {
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(credentials))
                }
            ) {
                assertEquals(HttpStatusCode.Forbidden, response.status())
                assertEquals(response.content, "Invalid username and/or password.")
            }
        }
        application.log.info("Test took $time")
    }
}
