package com.runetopic.api.tools.npc

import com.runetopic.mongodb.Document
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class Npc(
    override val uuid: String = newId<Npc>().toString(),
    val id: Int,
    val name: String
) : Document(uuid)
