package com.runetopic.topics

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.jwt.loginToken
import com.runetopic.module
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.confirmVerified
import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import java.util.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * @author Jordan Abraham
 */
class TopicControllerTest {

    @Test
    fun `test topic controller`() {
        val topic = mockk<Topic>()
        every { topic.id } returns UUID.randomUUID()
        every { topic.title } returns "Test Title"
        every { topic.description } returns "Test Description"
        every { topic.markdown } returns "<h1></h1>"
        every { topic.private } returns false

        withTestApplication({ module(testing = true) }) {
            handleRequest(HttpMethod.Get, "/api/topics") {
                addHeader("Authorization", "Bearer ${loginToken("test")}")
            }.apply {
                assertEquals(
                    HttpStatusCode.NotFound,
                    response.status()
                )
            }

            confirmVerified()

            handleRequest(HttpMethod.Post, "/api/topics") {
                addHeader("Authorization", "Bearer ${loginToken("test")}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(jacksonObjectMapper().writeValueAsString(topic))
            }.apply {
                assertEquals(
                    HttpStatusCode.Created,
                    response.status()
                )
            }

            confirmVerified()

            handleRequest(HttpMethod.Get, "/api/topics/${topic.id}") {
                addHeader("Authorization", "Bearer ${loginToken("test")}")
            }.apply {
                assertEquals(
                    jacksonObjectMapper().readValue(response.content, Topic::class.java),
                    topic
                )
                assertEquals(
                    HttpStatusCode.OK,
                    response.status()
                )
            }

            verify { topic.id }

            confirmVerified()

            handleRequest(HttpMethod.Put, "/api/topics/${topic.id}") {
                addHeader("Authorization", "Bearer ${loginToken("test")}")
                addHeader(HttpHeaders.ContentType, "application/json")
                setBody(
                    jacksonObjectMapper().writeValueAsString(
                        Topic(
                            topic.id,
                            "Changed Title Test",
                            topic.description,
                            topic.markdown,
                            topic.private
                        )
                    )
                )
            }.apply {
                with(jacksonObjectMapper().readValue(response.content, Topic::class.java)) {
                    assertNotEquals(topic.title, title)
                    assertEquals(topic.id, id)
                    assertEquals(topic.description, description)
                    assertEquals(topic.markdown, markdown)
                    assertEquals(topic.private, private)
                    assertEquals(HttpStatusCode.Accepted, response.status())
                }
            }

            verify { topic.id }
            verify { topic.title }
            verify { topic.description }
            verify { topic.markdown }
            verify { topic.private }

            confirmVerified()
        }
    }
}
