package com.runetopic.topics

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.module
import io.ktor.http.*
import io.ktor.server.testing.*
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * @author Jordan Abraham
 */
class TopicControllerTest {

    @Test
    fun `test topic controller`() {
        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/topics").apply {
                assertEquals(
                    HttpStatusCode.NotFound,
                    response.status()
                )
            }

            handleRequest(HttpMethod.Post, "/api/topics") {
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(
                    jacksonObjectMapper().writeValueAsString(
                        Topic(
                            title = "Test Title Here",
                            description = "Test Description...",
                            markdown = "<h1></h1>",
                            private = false
                        )
                    )
                )
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }
        }
    }
}
