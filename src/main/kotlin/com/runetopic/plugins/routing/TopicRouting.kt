package com.runetopic.plugins.routing

import com.runetopic.Authentications
import com.runetopic.api.topics.Topic
import com.runetopic.api.topics.TopicService
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.ktor.ext.inject
import org.litote.kmongo.eq

fun Application.configureTopicRouting() {

    val topicService by inject<TopicService>()

    routing {
        get("/api/topics") { call.respond(topicService.find<Topic>()) }
        get("/api/topics/{uuid}") {
            val uuid = call.parameters["uuid"]!!
            call.respond(HttpStatusCode.OK, topicService.findBy<Topic>(Topic::uuid eq uuid) ?: throw BadRequestException("Couldn't find Topic with uuid $uuid"))
        }
        authenticate(Authentications.LOGGED_IN) {
            post("/api/topics") {
                val topic = call.receive<Topic>()
                if (topicService.add(topic)) call.respond(HttpStatusCode.Created, topic)
            }
            put("/api/topics/{uuid}") {
                val uuid = call.parameters["uuid"]!!
                val topic = with(call.receive<Topic>()) {
                    // Retain the uuid of the topic.
                    Topic(uuid, title, description, markdown, private)
                }
                if (topicService.update(Topic::uuid eq uuid, topic)) call.respond(HttpStatusCode.Accepted, topic)
            }
        }
    }
}
