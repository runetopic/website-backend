package com.runetopic.api.tools.obj

import com.runetopic.api.topics.Topic
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class Obj(
    @BsonId
    val uuid: Id<Topic> = newId(),
    val id: Int,
    val name: String
) : Document
