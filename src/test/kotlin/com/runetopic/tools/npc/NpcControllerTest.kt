package com.runetopic.tools.npc

import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.tools.npc.Npc
import com.runetopic.api.tools.npc.NpcService
import com.runetopic.plugins.loginToken
import com.runetopic.testJacksonObjectMapper
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
import kotlin.time.measureTime

/**
 * @author Jordan Abraham
 */
class NpcControllerTest {

    @BeforeTest
    fun `clear npc storage`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            runBlocking {
                with(application.inject<NpcService>()) {
                    if (this.value.count<Npc>() != 0L) {
                        this.value.drop<Npc>()
                    }
                }
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test not authorized`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            with(handleRequest(HttpMethod.Get, "/api/tools/npcs")) {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get npcs empty`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            with(
                handleRequest(HttpMethod.Get, "/api/tools/npcs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                }
            ) {
                assertEquals(HttpStatusCode.OK, response.status())
                assertEquals("[ ]", response.content)
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test post npc`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val npc = mockk<Npc>()
            every { npc.uuid } returns newId()
            every { npc.id } returns 1
            every { npc.name } returns "Hans"

            with(
                handleRequest(HttpMethod.Post, "/api/tools/npcs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(npc))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get npcs`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val npc = mockk<Npc>()
            every { npc.uuid } returns newId()
            every { npc.id } returns 1
            every { npc.name } returns "Hans"

            with(
                handleRequest(HttpMethod.Post, "/api/tools/npcs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(npc))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            with(
                handleRequest(HttpMethod.Get, "/api/tools/npcs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                }
            ) {
                with(testJacksonObjectMapper().readValue(response.content, Array<Npc>::class.java).first()) {
                    assertEquals(npc.id, id)
                    assertEquals(npc.name, name)
                }
                assertEquals(HttpStatusCode.OK, response.status())
            }

            verify { npc.id }
            verify { npc.name }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }
}
