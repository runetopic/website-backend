package com.runetopic.tools.npc

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object NpcService : KoinComponent {
    private val npcStorage by inject<NpcStorage>()

    fun all(sorted: Boolean): Set<Npc> {
        if (npcStorage.storage.isNullOrEmpty()) throw NotFoundException()
        return when {
            sorted -> npcStorage.storage.toSortedSet(compareBy { it.id })
            else -> npcStorage.storage.toSet()
        }
    }

    fun add(npc: Npc) {
        if (!npcStorage.storage.add(npc)) {
            throw InternalServerErrorException()
        }
    }
}
