package com.runetopic.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.topics.Topic
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.plugins.loginToken
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

/**
 * @author Jordan Abraham
 */
class UserControllerTest {

    @BeforeTest
    fun `clear topic storage`() {
        withTestApplication(TestEnvironment) {
            runBlocking {
                with(application.inject<UserService>()) {
                    if (this.value.count<User>() != 0L) {
                        this.value.drop<User>()
                    }
                }
            }
        }
    }

    @Test
    fun `test get user details`() = withTestApplication(TestEnvironment) {
        val user = mockk<User>()
        every { user.uuid } returns newId<Topic>().toString()
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
                setBody(jacksonObjectMapper().writeValueAsString("user"))
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
}
