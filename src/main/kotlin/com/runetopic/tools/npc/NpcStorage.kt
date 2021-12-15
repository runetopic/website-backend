package com.runetopic.tools.npc

import java.util.*

/**
 * @author Jordan Abraham
 */
object NpcStorage {
    val storage = Collections.synchronizedSet(mutableSetOf<Npc>())
}
