package com.runetopic.api.tools.npc

import com.runetopic.api.AsyncService
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class NpcService : AsyncService<NpcStorage>() {
    override fun storage() = inject<NpcStorage>().value
}
