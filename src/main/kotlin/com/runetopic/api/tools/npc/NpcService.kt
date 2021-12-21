package com.runetopic.api.tools.npc

import com.runetopic.api.findAllAsync
import com.runetopic.api.insertOneAsync
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object NpcService : KoinComponent {

    private val npcStorage by inject<NpcStorage>()

    suspend fun all(sorted: Boolean): List<Npc> = with(npcStorage) {
        when {
            sorted -> findAllAsync().toList().sortedBy(Npc::id)
            else -> findAllAsync().toList()
        }
    }

    fun add(npc: Npc) = with(npcStorage) {
        insertOneAsync(npc)
    }
}
