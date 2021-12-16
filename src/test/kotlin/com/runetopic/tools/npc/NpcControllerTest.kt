package com.runetopic.tools.npc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.jwt.loginToken
import com.runetopic.module
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import org.koin.ktor.ext.inject
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class NpcControllerTest {

    @BeforeTest
    fun `clear npc storage`() = withTestApplication(Application::module) {
        with(application.inject<NpcStorage>()) { value.storage.clear() }
    }

    @Test
    fun `test not authorized`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/tools/npcs")) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }

    @Test
    fun `test get npcs empty`() = withTestApplication(Application::module) {
        with(handleRequest(HttpMethod.Get, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
        }) {
            assertEquals(HttpStatusCode.NotFound, response.status())
        }
    }

    @Test
    fun `test post npc`() = withTestApplication(Application::module) {
        val npc = mockk<Npc>()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(handleRequest(HttpMethod.Post, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(npc))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get npcs`() = withTestApplication(Application::module) {
        val npc = mockk<Npc>()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(handleRequest(HttpMethod.Post, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(npc))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        with(handleRequest(HttpMethod.Get, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test")}")
        }) {
            with(jacksonObjectMapper().readValue(response.content, Array<Npc>::class.java).first()) {
                assertEquals(npc.id, id)
                assertEquals(npc.name, name)
            }
            assertEquals(HttpStatusCode.OK, response.status())
        }

        verify { npc.id }
        verify { npc.name }

        confirmVerified()
    }
}
