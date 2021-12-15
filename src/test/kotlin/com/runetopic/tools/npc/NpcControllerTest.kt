package com.runetopic.tools.npc

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class NpcControllerTest {

    @Test
    fun `test npc controller`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/tools/npcs").apply {
                assertEquals(
                    HttpStatusCode.NotFound,
                    response.status()
                )
            }
        }

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/api/tools/npcs") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(Npc(1, "Hans")))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }

            handleRequest(HttpMethod.Get, "/api/tools/npcs").apply {
                with(jacksonObjectMapper().readValue(response.content, Array<Npc>::class.java).first()) {
                    assertEquals(1, id)
                    assertEquals("Hans", name)
                }

                assertEquals(
                    HttpStatusCode.OK,
                    response.status()
                )
            }
        }
    }
}
