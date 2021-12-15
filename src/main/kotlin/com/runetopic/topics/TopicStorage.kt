package com.runetopic.topics

import java.util.*

/**
 * @author Jordan Abraham
 */
object TopicStorage {
    val storage = Collections.synchronizedList(mutableListOf<Topic>())
}
