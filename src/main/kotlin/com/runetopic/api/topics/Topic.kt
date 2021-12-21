package com.runetopic.api.topics

import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class Topic(
    @BsonId
    val id: String = newId<Topic>().toString(),
    val title: String,
    val description: String,
    val markdown: String,
    val private: Boolean
)
