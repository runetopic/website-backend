package com.runetopic.api.topics

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun topicModule() = module {
    single { TopicStorage() }
    single { TopicService() }
}
