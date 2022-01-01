package com.runetopic.plugins

import com.runetopic.api.tools.npc.npcModule
import com.runetopic.api.tools.obj.objModule
import com.runetopic.api.tools.shop.shopModule
import com.runetopic.api.topics.topicModule
import com.runetopic.api.user.userModule
import com.runetopic.mongodb.MongoClient
import de.mkammerer.argon2.Argon2Factory
import io.ktor.application.*
import org.koin.dsl.module
import org.koin.ktor.ext.Koin

fun Application.installKoin() {
    install(Koin) {
        modules(
            // Misc module.
            module {
                single { MongoClient(this@installKoin) }
                single { Argon2Factory.create() }
            },
            // Core modules.
            objModule(),
            npcModule(),
            topicModule(),
            userModule(),
            shopModule()
        )
    }
}
