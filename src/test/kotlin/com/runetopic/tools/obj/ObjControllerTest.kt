package com.runetopic.tools.obj

import com.runetopic.TestEnvironment
import com.runetopic.TestEnvironment.JWT_TOKEN
import com.runetopic.api.tools.obj.Obj
import com.runetopic.api.tools.obj.ObjService
import com.runetopic.plugins.loginToken
import com.runetopic.testJacksonObjectMapper
import io.ktor.application.*
import io.ktor.config.*
import io.ktor.http.*
import io.ktor.server.testing.*
import io.mockk.*
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
class ObjControllerTest {

    @BeforeTest
    fun `clear obj storage`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            runBlocking {
                with(application.inject<ObjService>()) {
                    if (this.value.count<Obj>() != 0L) {
                        this.value.drop<Obj>()
                    }
                }
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test not authorized`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            with(handleRequest(HttpMethod.Get, "/api/tools/objs")) {
                assertEquals(HttpStatusCode.Unauthorized, response.status())
            }
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get objs empty`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            with(
                handleRequest(HttpMethod.Get, "/api/tools/objs") {
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
    fun `test post obj`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val obj = mockk<Obj>()
            every { obj.uuid } returns newId()
            every { obj.id } returns 4151
            every { obj.name } returns "Abyssal Whip"

            with(
                handleRequest(HttpMethod.Post, "/api/tools/objs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(obj))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }

    @Test
    fun `test get objs`() = withTestApplication(TestEnvironment) {
        val time = measureTime {
            val obj = mockk<Obj>()
            every { obj.uuid } returns newId()
            every { obj.id } returns 4151
            every { obj.name } returns "Abyssal Whip"

            with(
                handleRequest(HttpMethod.Post, "/api/tools/objs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                    addHeader(HttpHeaders.ContentType, "application/json")
                    setBody(testJacksonObjectMapper().writeValueAsString(obj))
                }
            ) {
                assertEquals(HttpStatusCode.Created, response.status())
            }

            with(
                handleRequest(HttpMethod.Get, "/api/tools/objs") {
                    addHeader("Authorization", "Bearer ${loginToken("test", JWT_TOKEN)}")
                }
            ) {
                with(testJacksonObjectMapper().readValue(response.content, Array<Obj>::class.java).first()) {
                    assertEquals(obj.id, id)
                    assertEquals(obj.name, name)
                }
                assertEquals(HttpStatusCode.OK, response.status())
            }

            verify { obj.id }
            verify { obj.name }

            confirmVerified()
        }
        application.log.info("Test took $time")
    }
}
