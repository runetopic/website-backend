package com.runetopic.api.tools.npc

import com.runetopic.mongodb.AsyncService
import com.runetopic.mongodb.AsyncStorage

/**
 * @author Jordan Abraham
 */
class NpcService(
    storage: AsyncStorage
) : AsyncService(storage) {

    suspend fun getNPCSpawns189(): List<NPCSpawns189> {
        return getCollection<NPCSpawns189>()
            .find()
            .batchSize(5000)
            .descendingSort(NPCSpawns189::id)
            .toList()
    }
}
