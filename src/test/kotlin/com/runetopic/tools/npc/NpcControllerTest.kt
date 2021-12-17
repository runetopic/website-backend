package com.runetopic.tools.npc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.TEST_KEY
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.tools.npc.NpcStorage
import com.runetopic.module
import com.runetopic.plugins.loginToken
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
    fun `clear npc storage`() = withTestApplication(TestEnvironment) {
        with(application.inject<NpcStorage>()) { value.storage.clear() }
    }

    @Test
    fun `test not authorized`() = withTestApplication(TestEnvironment) {
        with(handleRequest(HttpMethod.Get, "/api/tools/npcs")) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }

    @Test
    fun `test get npcs empty`() = withTestApplication(TestEnvironment) {
        with(handleRequest(HttpMethod.Get, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
        }) {
            assertEquals(HttpStatusCode.NotFound, response.status())
        }
    }

    @Test
    fun `test post npc`() = withTestApplication(TestEnvironment) {
        val npc = mockk<Npc>()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(handleRequest(HttpMethod.Post, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(npc))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get npcs`() = withTestApplication(TestEnvironment) {
        val npc = mockk<Npc>()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(handleRequest(HttpMethod.Post, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(npc))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        with(handleRequest(HttpMethod.Get, "/api/tools/npcs") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
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
