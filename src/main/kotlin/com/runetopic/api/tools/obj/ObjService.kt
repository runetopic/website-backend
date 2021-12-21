package com.runetopic.api.tools.obj

import com.runetopic.api.findAllAsync
import com.runetopic.api.insertOneAsync
import kotlinx.coroutines.runBlocking
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

/**
 * @author Jordan Abraham
 */
object ObjService : KoinComponent {

    private val objStorage by inject<ObjStorage>()

    suspend fun all(sorted: Boolean): List<Obj> = with(objStorage) {
        when {
            sorted -> findAllAsync().toList().sortedBy(Obj::id)
            else -> findAllAsync().toList()
        }
    }

    fun add(obj: Obj) = with(objStorage) {
        insertOneAsync(obj)
    }
}
