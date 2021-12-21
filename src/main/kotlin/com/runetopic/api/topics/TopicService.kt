package com.runetopic.api.topics

import com.runetopic.api.findAllAsync
import com.runetopic.api.findOneAsync
import com.runetopic.api.insertOneAsync
import com.runetopic.api.updateOneAsync
import io.ktor.features.*
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import org.litote.kmongo.Id
import org.litote.kmongo.eq

/**
 * @author Jordan Abraham
 */
object TopicService : KoinComponent {

    private val topicStorage by inject<TopicStorage>()

    fun all(): List<Topic> = runBlocking {
        with(topicStorage) {
            findAllAsync().toList()
        }
    }

//    fun all(): Set<Topic> = with(topicStorage) {
//        if (storage.isNullOrEmpty()) emptySet<Topic>()
//        storage.toSet()
//    }

    fun add(topic: Topic) = runBlocking {
        with(topicStorage) {
            insertOneAsync(topic)
        }
    }

//    fun add(topic: Topic) = with(topicStorage) {
//        if (!storage.add(topic)) {
//            throw InternalServerErrorException()
//        }
//    }

//    fun update(id: UUID, topic: Topic) = with(topicStorage) {
//        if (storage.none { it.id == id }) throw BadRequestException("Couldn't update Topic with uuid $id")
//        storage[storage.indexOfFirst { it.id == id }] = topic
//    }

    fun update(id: String, topic: Topic) = runBlocking {
        with(topicStorage) {
            updateOneAsync(Topic::id eq id, topic)
        }
    }

    fun one(id: String): Topic = runBlocking {
        with(topicStorage) {
            println(id)
            findOneAsync(Topic::id eq id) ?: throw BadRequestException("Couldn't find Topic with uuid $id")
        }
    }

//    fun one(id: UUID): Topic = with(topicStorage) {
//        if (storage.none { it.id == id }) throw BadRequestException("Couldn't find Topic with uuid $id")
//        storage[storage.indexOfFirst { it.id == id }]
//    }
}
