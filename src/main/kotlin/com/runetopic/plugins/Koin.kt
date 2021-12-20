package com.runetopic.plugins

import com.runetopic.api.tools.npc.npcModule
import com.runetopic.api.tools.obj.objModule
import com.runetopic.api.topics.topicModule
import com.runetopic.api.user.userModule
import io.ktor.application.*
import org.koin.ktor.ext.Koin

fun Application.installKoin() {
    install(Koin) {
        modules(
            objModule(),
            npcModule(),
            topicModule(),
            userModule()
        )
    }
}
