package com.runetopic.tools.obj

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.module
import com.runetopic.tools.npc.Npc
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class NpcControllerTest {

    @Test
    fun `test npcs not found`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/npcs").apply {
                assertEquals(
                    HttpStatusCode.NotFound,
                    response.status()
                )
            }
        }
    }

    @Test
    fun `test npc created`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/npcs") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(Npc(1, "Hans")))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }
        }
    }

    @Test
    fun `test npc exists`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/npcs") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(Npc(1, "Hans")))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }

            handleRequest(HttpMethod.Get, "/npcs").apply {
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
