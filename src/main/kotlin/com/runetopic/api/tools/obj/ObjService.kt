package com.runetopic.api.tools.obj

import com.runetopic.api.AsyncService
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class ObjService : AsyncService<ObjStorage>() {
    override fun storage() = inject<ObjStorage>().value
}
