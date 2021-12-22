package com.runetopic.api.topics

import com.fasterxml.jackson.annotation.JsonFormat
import com.fasterxml.jackson.databind.annotation.JsonSerialize
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId
import java.time.ZonedDateTime

/**
 * @author Jordan Abraham
 */
data class Topic(
    @BsonId
    @JsonSerialize(using = ToStringSerializer::class)
    val uuid: Id<Topic> = newId(),
    val title: String,
    val description: String,
    val markdown: String,
    val private: Boolean,
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSZ")
    val createDate: ZonedDateTime = ZonedDateTime.now()
) : Document
