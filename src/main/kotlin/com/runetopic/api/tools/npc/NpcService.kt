package com.runetopic.api.tools.npc

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class NpcService : KoinComponent {

    private val npcStorage by inject<NpcStorage>()

    suspend fun all(sorted: Boolean): List<Npc> = with(npcStorage) {
        when {
            sorted -> find<Npc>().sortedBy(Npc::id)
            else -> find()
        }
    }

    suspend fun add(npc: Npc) = with(npcStorage) {
        insertOne(npc)
    }
}
