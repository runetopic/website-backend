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
import org.bson.types.ObjectId
import org.koin.ktor.ext.inject
import org.litote.kmongo.id.toId

fun Application.configureTopicRouting() {

    val topicService by inject<TopicService>()

    routing {
        get("/api/topics") { call.respond(topicService.all()) }
        get("/api/topics/{id}") {
            val id = call.parameters["id"]!!
            call.respond(HttpStatusCode.OK, topicService.one(id))
        }
        authenticate(Authentications.LOGGED_IN) {
            post("/api/topics") {
                val topic = call.receive<Topic>()
                topicService.add(topic)
                call.respond(HttpStatusCode.Created, topic)
            }
            put("/api/topics/{id}") {
                val id = call.parameters["id"]!!
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
