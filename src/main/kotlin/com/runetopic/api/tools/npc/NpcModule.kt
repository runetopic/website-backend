package com.runetopic.api.tools.npc

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun npcModule() = module {
    single { NpcStorage }
    single { NpcService }
}
