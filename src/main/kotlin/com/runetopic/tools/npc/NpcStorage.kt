package com.runetopic.tools.npc

import java.util.*

/**
 * @author Jordan Abraham
 */
class NpcStorage {
    val storage = Collections.synchronizedSet(mutableSetOf<Npc>())
}
