package com.runetopic.tools.obj

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object ObjService : KoinComponent {
    private val objStorage by inject<ObjStorage>()

    fun all(sorted: Boolean): Set<Obj> {
        if (objStorage.storage.isNullOrEmpty()) throw NotFoundException()
        return when {
            sorted -> objStorage.storage.toSortedSet(compareBy { it.id })
            else -> objStorage.storage.toSet()
        }
    }

    fun add(obj: Obj) {
        if (!objStorage.storage.add(obj)) {
            throw InternalServerErrorException()
        }
    }
}
