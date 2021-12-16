package com.runetopic.api.tools.npc

import java.util.*

/**
 * @author Jordan Abraham
 */
object NpcStorage {
    val storage = Collections.synchronizedList(mutableListOf<Npc>())
}
