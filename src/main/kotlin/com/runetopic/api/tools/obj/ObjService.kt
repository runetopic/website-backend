package com.runetopic.api.tools.obj

import com.runetopic.api.AsyncService
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class ObjService : KoinComponent, AsyncService<ObjStorage>() {
    override fun storage() = inject<ObjStorage>().value
}
