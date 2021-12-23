package com.runetopic.topics

import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.topics.Topic
import com.runetopic.api.topics.TopicService
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
import java.time.ZonedDateTime
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotEquals
import kotlin.time.measureTime

/**
 * @author Jordan Abraham
 */
class TopicControllerTest {

    @BeforeTest
    fun `clear topic storage`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            runBlocking {
                with(application.inject<TopicService>()) {
                    if (this.value.count<Topic>() != 0L) {
                        this.value.drop<Topic>()
                    }
                }
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get topics empty`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            with(
                handleRequest(HttpMethod.Get, "/api/topics") {
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
    fun `test post topic`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val topic = mockk<Topic>()
            every { topic.uuid } returns newId()
            every { topic.title } returns "Test Title"
            every { topic.description } returns "Test Description"
            every { topic.markdown } returns "<h1></h1>"
            every { topic.private } returns false
            every { topic.createDate } returns ZonedDateTime.parse("2021-12-22T07:18:25.019Z[UTC]")

            with(
                handleRequest(HttpMethod.Post, "/api/topics") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(topic))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get topics`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val topic = mockk<Topic>()
            every { topic.uuid } returns newId()
            every { topic.title } returns "Test Title"
            every { topic.description } returns "Test Description"
            every { topic.markdown } returns "<h1></h1>"
            every { topic.private } returns false
            every { topic.createDate } returns ZonedDateTime.parse("2021-12-22T07:18:25.019Z[UTC]")

            with(
                handleRequest(HttpMethod.Post, "/api/topics") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(topic))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            confirmVerified()

            with(
                handleRequest(HttpMethod.Get, "/api/topics/${topic.uuid}") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                }
            ) {
                assertEquals(testJacksonObjectMapper().readValue(response.content, Topic::class.java), topic)
                assertEquals(HttpStatusCode.OK, response.status())
            }
            verify { topic.uuid }

            confirmVerified()

            with(
                handleRequest(HttpMethod.Put, "/api/topics/${topic.uuid}") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(
                        testJacksonObjectMapper().writeValueAsString(
                            Topic(
                                topic.uuid,
                                "Changed Title Test",
                                topic.description,
                                topic.markdown,
                                topic.private,
                                topic.createDate
                            )
                        )
                    )
                }
            ) {
                with(testJacksonObjectMapper().readValue(response.content, Topic::class.java)) {
                    assertNotEquals(topic.title, title)
                    assertEquals(topic.uuid, uuid)
                    assertEquals(topic.description, description)
                    assertEquals(topic.markdown, markdown)
                    assertEquals(topic.private, private)
                    assertEquals(HttpStatusCode.Accepted, response.status())
                }
            }

            verify { topic.uuid }
            verify { topic.title }
            verify { topic.description }
            verify { topic.markdown }
            verify { topic.private }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }
}
