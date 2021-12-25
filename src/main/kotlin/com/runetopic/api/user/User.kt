package com.runetopic.api.user

import com.fasterxml.jackson.annotation.JsonFormat
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.ZonedDateTime

/**
 * @author Jordan Abraham
 */
data class User(
    @BsonId
    val uuid: Id<User> = newId(),
    val username: String,
    val password: String,
    val email: String,
    val dateOfBirth: String,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    val createDate: ZonedDateTime = ZonedDateTime.now()
) : Document
