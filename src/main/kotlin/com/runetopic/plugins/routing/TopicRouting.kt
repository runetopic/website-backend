package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.topics.Topic
import com.runetopic.api.topics.TopicService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import java.util.*

fun Application.configureTopicRouting() {

    val topicService by inject<TopicService>()

    routing {
        authenticate(Authentications.LOGGED_IN) {
            get("/api/topics") { call.respond(topicService.all()) }
            post("/api/topics") {
                val topic = call.receive<Topic>()
                topicService.add(topic)
                call.respond(HttpStatusCode.Created, topic)
            }

            get("/api/topics/{id}") {
                val id = UUID.fromString(call.parameters["id"])
                call.respond(HttpStatusCode.OK, topicService.one(id))
            }
            put("/api/topics/{id}") {
                val id = UUID.fromString(call.parameters["id"])
                val topic = with(call.receive<Topic>()) {
                    // Retain the id of the topic.
                    Topic(id, title, description, markdown, private)
                }
                topicService.update(id, topic)
                call.respond(HttpStatusCode.Accepted, topic)
            }
        }
    }
}
