package com.runetopic.api.tools.obj

import com.runetopic.mongodb.Document
import org.litote.kmongo.newId

/**
 * @author Jordan Abraham
 */
data class Obj(
    override val uuid: String = newId<Obj>().toString(),
    val id: Int,
    val name: String
) : Document(uuid)
