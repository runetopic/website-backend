package com.runetopic.tools.obj

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.tools.obj.Obj
import com.runetopic.api.tools.obj.ObjStorage
import com.runetopic.api.topics.Topic
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class ObjControllerTest {
    @BeforeTest
    fun `clear obj storage`() {
        withTestApplication(TestEnvironment) {
            runBlocking {
                with(application.inject<ObjStorage>()) {
                    val collection = value.database().getCollection<Topic>(this.value.collection())
                    if (collection.countDocuments() != 0L) {
                        collection.drop()
                    }
                }
            }
        }
    }

    @Test
    fun `test not authorized`() = withTestApplication(TestEnvironment) {
        with(handleRequest(HttpMethod.Get, "/api/tools/objs")) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }

    @Test
    fun `test get objs empty`() = withTestApplication(TestEnvironment) {
        with(
            handleRequest(HttpMethod.Get, "/api/tools/objs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
            }
        ) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("[ ]", response.content)
        }
    }

    @Test
    fun `test post obj`() = withTestApplication(TestEnvironment) {
        val obj = mockk<Obj>()
        every { obj.id } returns 4151
        every { obj.name } returns "Abyssal Whip"

        with(
            handleRequest(HttpMethod.Post, "/api/tools/objs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(obj))
            }
        ) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get objs`() = withTestApplication(TestEnvironment) {
        val obj = mockk<Obj>()
        every { obj.id } returns 4151
        every { obj.name } returns "Abyssal Whip"

        with(
            handleRequest(HttpMethod.Post, "/api/tools/objs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(obj))
            }
        ) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        with(
            handleRequest(HttpMethod.Get, "/api/tools/objs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
            }
        ) {
            with(jacksonObjectMapper().readValue(response.content, Array<Obj>::class.java).first()) {
                assertEquals(obj.id, id)
                assertEquals(obj.name, name)
            }
            assertEquals(HttpStatusCode.OK, response.status())
        }

        verify { obj.id }
        verify { obj.name }

        confirmVerified()
    }
}
