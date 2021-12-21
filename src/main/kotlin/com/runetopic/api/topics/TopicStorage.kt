package com.runetopic.api.topics

import com.runetopic.api.AsyncStorage
import org.koin.core.component.KoinComponent

/**
 * @author Jordan Abraham
 */
class TopicStorage : KoinComponent, AsyncStorage<Topic>()
