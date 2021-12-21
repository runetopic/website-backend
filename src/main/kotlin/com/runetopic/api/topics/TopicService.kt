package com.runetopic.api.topics

import com.runetopic.mongodb.AsyncService
import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class TopicService(
    storage: AsyncStorage
) : AsyncService(storage)
