package com.runetopic.tools.npc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.tools.npc.NpcService
import com.runetopic.api.topics.Topic
import com.runetopic.plugins.loginToken
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import kotlinx.coroutines.runBlocking
import org.koin.ktor.ext.inject
import org.litote.kmongo.newId
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class NpcControllerTest {

    @BeforeTest
    fun `clear npc storage`() {
        withTestApplication(TestEnvironment) {
            runBlocking {
                with(application.inject<NpcService>()) {
                    if (this.value.count<Npc>() != 0L) {
                        this.value.drop<Npc>()
                    }
                }
            }
        }
    }

    @Test
    fun `test not authorized`() = withTestApplication(TestEnvironment) {
        with(handleRequest(HttpMethod.Get, "/api/tools/npcs")) {
            assertEquals(HttpStatusCode.Unauthorized, response.status())
        }
    }

    @Test
    fun `test get npcs empty`() = withTestApplication(TestEnvironment) {
        with(
            handleRequest(HttpMethod.Get, "/api/tools/npcs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
            }
        ) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("[ ]", response.content)
        }
    }

    @Test
    fun `test post npc`() = withTestApplication(TestEnvironment) {
        val npc = mockk<Npc>()
        every { npc.uuid } returns newId<Topic>().toString()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(
            handleRequest(HttpMethod.Post, "/api/tools/npcs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(npc))
            }
        ) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get npcs`() = withTestApplication(TestEnvironment) {
        val npc = mockk<Npc>()
        every { npc.uuid } returns newId<Topic>().toString()
        every { npc.id } returns 1
        every { npc.name } returns "Hans"

        with(
            handleRequest(HttpMethod.Post, "/api/tools/npcs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(npc))
            }
        ) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        with(
            handleRequest(HttpMethod.Get, "/api/tools/npcs") {
                addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
            }
        ) {
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
