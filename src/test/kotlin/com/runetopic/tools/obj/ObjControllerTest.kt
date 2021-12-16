package com.runetopic.tools.obj

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.jwt.loginToken
import com.runetopic.module
import com.runetopic.tools.npc.NpcStorage
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
import org.koin.core.context.stopKoin
import org.koin.ktor.ext.inject
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class ObjControllerTest {

    @BeforeTest
    fun `clear obj storage`() = withTestApplication(Application::module) {
        with(application.inject<ObjStorage>()) { value.storage.clear() }
    }

    @Test
    fun `test not authorized`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/tools/objs")) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }

    @Test
    fun `test get objs empty`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/tools/objs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
        }) {
            assertEquals(HttpStatusCode.NotFound, response.status())
        }
    }

    @Test
    fun `test post obj`() = withTestApplication(Application::module) {
        val obj = mockk<Obj>()
        every { obj.id } returns 4151
        every { obj.name } returns "Abyssal Whip"

        with(handleRequest(HttpMethod.Post, "/api/tools/objs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(obj))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get objs`() = withTestApplication(Application::module) {
        val obj = mockk<Obj>()
        every { obj.id } returns 4151
        every { obj.name } returns "Abyssal Whip"

        with(handleRequest(HttpMethod.Post, "/api/tools/objs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(obj))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        with(handleRequest(HttpMethod.Get, "/api/tools/objs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
        }) {
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
