package com.runetopic.api.user

import com.runetopic.api.topics.Topic
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class User(
    @BsonId
    val uuid: Id<Topic> = newId(),
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String
) : Document
