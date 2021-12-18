package com.runetopic.api.topics

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import io.ktor.features.*
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * @author Jordan Abraham
 */
object TopicService : KoinComponent {
    private val topicStorage by inject<TopicStorage>()

    fun all(): Set<Topic> = with(topicStorage) {
        if (storage.isNullOrEmpty()) emptySet<Topic>()
        storage.toSet()
    }

    fun add(topic: Topic) = with(topicStorage) {
        if (!storage.add(topic)) {
            throw InternalServerErrorException()
        }
    }

    fun update(id: UUID, topic: Topic) = with(topicStorage) {
        if (storage.none { it.id == id }) throw BadRequestException("Couldn't update Topic with uuid $id")
        storage[storage.indexOfFirst { it.id == id }] = topic
    }

    fun one(id: UUID): Topic = with(topicStorage) {
        if (storage.none { it.id == id })  throw BadRequestException("Couldn't find Topic with uuid $id")
        storage[storage.indexOfFirst { it.id == id }]
    }
}
