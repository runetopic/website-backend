package com.runetopic.user

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.TEST_KEY
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.topics.TopicStorage
import com.runetopic.api.user.User
import com.runetopic.api.user.UserService
import com.runetopic.api.user.UserStorage
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.auth.jwt.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import org.koin.ktor.ext.inject
import java.util.*
import kotlin.collections.ArrayList
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class UserControllerTest {

    @BeforeTest
    fun `clear user storage`() = withTestApplication(TestEnvironment) {
        with(application.inject<UserStorage>()) {
            value.storage.clear()
        }
    }

    @Test
    fun `test get user details`() = withTestApplication(TestEnvironment) {
        val user = mockk<User>()
        every { user.username } returns "test"
        every { user.email } returns "test"
        every { user.dateOfBirth } returns "1/1/1111"
        every { user.password } returns "test"
        UserStorage.storage.add(user)

        with(handleRequest(HttpMethod.Get, "/api/user/details") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString("user"))
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("{\n" +
                    "  \"email\" : \"${user.email}\",\n" +
                    "  \"username\" : \"${user.username}\"\n" +
                    "}", response.content)
        }

        confirmVerified()
    }
}
