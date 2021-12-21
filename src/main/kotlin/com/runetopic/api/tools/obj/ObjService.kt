package com.runetopic.api.tools.obj

import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
class ObjService : KoinComponent {

    private val objStorage by inject<ObjStorage>()

    suspend fun all(sorted: Boolean): List<Obj> = with(objStorage) {
        when {
            sorted -> find<Obj>().sortedBy(Obj::id)
            else -> find()
        }
    }

    suspend fun add(obj: Obj) = with(objStorage) {
        insertOne(obj)
    }
}
