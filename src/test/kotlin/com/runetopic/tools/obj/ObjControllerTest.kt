package com.runetopic.tools.obj

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class ObjControllerTest {

    @Test
    fun `test objs not found`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/objs").apply {
                assertEquals(
                    HttpStatusCode.NotFound,
                    response.status()
                )
            }
        }
    }

    @Test
    fun `test obj created`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/objs") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(Obj(4151, "Abyssal Whip")))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }
        }
    }

    @Test
    fun `test obj exists`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Post, "/objs") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(Obj(4151, "Abyssal Whip")))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }

            handleRequest(HttpMethod.Get, "/objs").apply {
                with(jacksonObjectMapper().readValue(response.content, Array<Obj>::class.java).first()) {
                    assertEquals(4151, id)
                    assertEquals("Abyssal Whip", name)
                }

                assertEquals(
                    HttpStatusCode.OK,
                    response.status()
                )
            }
        }
    }
}
