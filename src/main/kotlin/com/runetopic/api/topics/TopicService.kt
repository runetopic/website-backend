package com.runetopic.api.topics

import com.runetopic.api.findAllAsync
import com.runetopic.api.findOneAsync
import com.runetopic.api.insertOneAsync
import com.runetopic.api.updateOneAsync
import io.ktor.features.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
object TopicService : KoinComponent {

    private val topicStorage by inject<TopicStorage>()

    suspend fun all(): List<Topic> = with(topicStorage) {
        findAllAsync().toList()
    }

    fun add(topic: Topic): Topic = with(topicStorage) {
        insertOneAsync(topic)
        topic
    }

    fun update(id: String, topic: Topic) = with(topicStorage) {
        updateOneAsync(Topic::id eq id, topic)
    }

    fun findById(id: String): Topic = with(topicStorage) {
        findOneAsync(Topic::id eq id) ?: throw BadRequestException("Couldn't find Topic with uuid $id")
    }
}
