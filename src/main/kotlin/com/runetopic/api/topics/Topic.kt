package com.runetopic.api.topics

import com.runetopic.mongodb.Document
import org.litote.kmongo.newId
import java.util.*

/**
 * @author Jordan Abraham
 */
data class Topic(
    override val uuid: String = newId<Topic>().toString(),
    val title: String,
    val description: String,
    val markdown: String,
    val private: Boolean,
    val createDate: Date = Date()
) : Document(uuid)
