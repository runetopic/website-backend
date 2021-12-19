package com.runetopic.topics

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.TEST_KEY
import com.runetopic.api.topics.Topic
import com.runetopic.api.topics.TopicStorage
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
import java.util.*
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals

/**
 * @author Jordan Abraham
 */
class TopicControllerTest {

    @BeforeTest
    fun `clear topic storage`() = withTestApplication(TestEnvironment) {
        with(application.inject<TopicStorage>()) { value.storage.clear() }
    }

    @Test
    fun `test get topics empty`() = withTestApplication(TestEnvironment) {
        with(handleRequest(HttpMethod.Get, "/api/topics") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
        }) {
            assertEquals(HttpStatusCode.OK, response.status())
            assertEquals("[ ]", response.content)
        }
    }

    @Test
    fun `test post topic`() = withTestApplication(TestEnvironment) {
        val topic = mockk<Topic>()
        every { topic.id } returns UUID.randomUUID()
        every { topic.title } returns "Test Title"
        every { topic.description } returns "Test Description"
        every { topic.markdown } returns "<h1></h1>"
        every { topic.private } returns false

        with(handleRequest(HttpMethod.Post, "/api/topics") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(topic))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()
    }

    @Test
    fun `test get topics`() = withTestApplication(TestEnvironment) {
        val topic = mockk<Topic>()
        every { topic.id } returns UUID.randomUUID()
        every { topic.title } returns "Test Title"
        every { topic.description } returns "Test Description"
        every { topic.markdown } returns "<h1></h1>"
        every { topic.private } returns false

        with(handleRequest(HttpMethod.Post, "/api/topics") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
            addHeader(HttpHeaders.ContentType, "application/json")
            setBody(jacksonObjectMapper().writeValueAsString(topic))
        }) {
            assertEquals(HttpStatusCode.Created, response.status())
        }

        confirmVerified()

        with(handleRequest(HttpMethod.Get, "/api/topics/${topic.id}") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
        }) {
            assertEquals(jacksonObjectMapper().readValue(response.content, Topic::class.java), topic)
            assertEquals(HttpStatusCode.OK, response.status())
        }
        verify { topic.id }

        confirmVerified()

        with(handleRequest(HttpMethod.Put, "/api/topics/${topic.id}") {
            addHeader("Authorization", "Bearer ${loginToken("test", TEST_KEY)}")
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
        }) {
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
