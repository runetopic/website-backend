package com.runetopic.api.topics

import com.runetopic.api.AsyncService
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class TopicService : AsyncService<TopicStorage>() {
    override fun storage() = inject<TopicStorage>().value
}
