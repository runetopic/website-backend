package com.runetopic.api.tools.obj

import com.runetopic.exception.InternalServerErrorException
import com.runetopic.exception.NotFoundException
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object ObjService : KoinComponent {
    private val objStorage by inject<ObjStorage>()

    fun all(sorted: Boolean): Set<Obj> = with(objStorage) {
        if (storage.isNullOrEmpty()) throw NotFoundException()
        when {
            sorted -> storage.toSortedSet(compareBy { it.id })
            else -> storage.toSet()
        }
    }

    fun add(obj: Obj) = with(objStorage) {
        if (!storage.add(obj)) {
            throw InternalServerErrorException()
        }
    }
}
