package com.runetopic.user

import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.plugins.loginToken
import com.runetopic.testJacksonObjectMapper
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import org.litote.kmongo.newId
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.time.measureTime

/**
 * @author Jordan Abraham
 */
class UserControllerTest {

    @BeforeTest
    fun `clear topic storage`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            runBlocking {
                with(application.inject<UserService>()) {
                    if (this.value.count<User>() != 0L) {
                        this.value.drop<User>()
                    }
                }
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get user details`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val user = mockk<User>()
            every { user.uuid } returns newId()
            every { user.username } returns "test"
            every { user.email } returns "test"
            every { user.dateOfBirth } returns "1/1/1111"
            every { user.password } returns "test"

            val userService by application.inject<UserService>()

            runBlocking {
                userService.add(user)
            }

            with(
                handleRequest(HttpMethod.Get, "/api/user/details") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString("user"))
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals(
                    "{\n" +
                        "  \"username\" : \"${user.email}\",\n" +
                        "  \"email\" : \"${user.username}\"\n" +
                        "}",
                    response.content
                )
            }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }
}
