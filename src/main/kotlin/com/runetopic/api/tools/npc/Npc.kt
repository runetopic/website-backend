package com.runetopic.api.tools.npc

import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class Npc(
    @BsonId
    val uuid: Id<Npc> = newId(),
    val id: Int,
    val name: String
) : Document
