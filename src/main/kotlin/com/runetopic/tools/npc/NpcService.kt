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

    fun all(sorted: Boolean): Set<Npc> = with(npcStorage) {
        if (storage.isNullOrEmpty()) throw NotFoundException()
        when {
            sorted -> storage.toSortedSet(compareBy { it.id })
            else -> storage.toSet()
        }
    }

    fun add(npc: Npc) = with(npcStorage) {
        if (!storage.add(npc)) {
            throw InternalServerErrorException()
        }
    }
}
