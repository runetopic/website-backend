package com.runetopic.topics

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * @author Jordan Abraham
 */
class TopicController(
    routing: Routing
) : KoinComponent {

    init {
        val topicService by inject<TopicService>()

        with(routing) {
            route("/api/topics") {
                get { call.respond(topicService.all()) }
                post {
                    val topic = call.receive<Topic>()
                    topicService.add(topic)
                    call.respond(HttpStatusCode.Created)
                }
            }
            route("/api/topics/{id}") {
                get {
                    val id = UUID.fromString(call.parameters["id"])
                    call.respond(topicService.one(id))
                }
                put {
                    val id = UUID.fromString(call.parameters["id"])
                    val topic = with(call.receive<Topic>()) {
                        // Retain the id of the topic.
                        Topic(id, title, description, markdown, private)
                    }
                    topicService.update(id, topic)
                    call.respond(HttpStatusCode.Accepted)
                }
            }
        }
    }
}
