package com.runetopic.api.topics

import io.ktor.features.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
class TopicService : KoinComponent {

    private val topicStorage by inject<TopicStorage>()

    suspend fun all(): List<Topic> = with(topicStorage) {
        find()
    }

    suspend fun add(topic: Topic): Topic = with(topicStorage) {
        insertOne(topic)
        topic
    }

    suspend fun update(id: String, topic: Topic) = with(topicStorage) {
        updateOne(Topic::id eq id, topic)
    }

    suspend fun findById(id: String): Topic = with(topicStorage) {
        findOne(Topic::id eq id) ?: throw BadRequestException("Couldn't find Topic with uuid $id")
    }
}
