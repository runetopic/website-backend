package com.runetopic.api.tools.npc

import com.fasterxml.jackson.annotation.JsonIgnore
import com.runetopic.mongodb.Document
import org.bson.codecs.pojo.annotations.BsonId
import org.litote.kmongo.Id
import org.litote.kmongo.newId

data class NPCSpawns189(
    @BsonId
    @JsonIgnore
    val uuid: Id<Npc> = newId(),
    val id: Int,
    val x: Int,
    val y: Int,
    val z: Int,
    val direction: Int
): Document
