package com.runetopic.topics

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.util.*

/**
 * @author Jordan Abraham
 */
object TopicService : KoinComponent {
    private val topicStorage by inject<TopicStorage>()

    fun all(): Set<Topic> = with(topicStorage) {
        if (storage.isNullOrEmpty()) throw NotFoundException()
        storage.toSet()
    }

    fun add(topic: Topic) = with(topicStorage) {
        if (!storage.add(topic)) {
            throw InternalServerErrorException()
        }
    }

    fun update(id: UUID, topic: Topic) = with(topicStorage) {
        if (storage.none { it.id == id }) throw NotFoundException()
        storage[storage.indexOfFirst { it.id == id }] = topic
    }

    fun one(id: UUID): Topic = with(topicStorage) {
        if (storage.none { it.id == id }) throw NotFoundException()
        storage[storage.indexOfFirst { it.id == id }]
    }
}
