package com.runetopic.api.topics

import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class TopicStorage(
    database: String
) : AsyncStorage(database)
