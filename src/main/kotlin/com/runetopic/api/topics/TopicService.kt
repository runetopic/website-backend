package com.runetopic.api.topics

import com.runetopic.api.AsyncService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class TopicService : KoinComponent, AsyncService<TopicStorage>() {
    override fun storage() = inject<TopicStorage>().value
}
