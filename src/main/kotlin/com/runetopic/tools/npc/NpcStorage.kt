package com.runetopic.tools.npc

import java.util.*

/**
 * @author Jordan Abraham
 */
object NpcStorage {
    val storage = Collections.synchronizedList(mutableListOf<Npc>())
}
