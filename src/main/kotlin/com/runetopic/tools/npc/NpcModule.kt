package com.runetopic.tools.npc

import org.koin.dsl.module

/**
 * @author Jordan Abraham
 */
fun npcModule() = module {
    single { NpcStorage() }
    single { NpcService() }
}
