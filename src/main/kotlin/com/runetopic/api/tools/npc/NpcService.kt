package com.runetopic.api.tools.npc

import com.runetopic.api.AsyncService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class NpcService : KoinComponent, AsyncService<NpcStorage>() {
    override fun storage() = inject<NpcStorage>().value
}
