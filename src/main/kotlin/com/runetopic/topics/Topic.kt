package com.runetopic.topics

import java.util.*

/**
 * @author Jordan Abraham
 */
data class Topic(
    val id: UUID = UUID.randomUUID(),
    val title: String,
    val description: String,
    val markdown: String,
    val private: Boolean
)
